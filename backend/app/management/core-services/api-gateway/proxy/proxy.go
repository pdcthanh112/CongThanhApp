package proxy

import (
    "bytes"
    "context"
    "fmt"
    "io"
    "net/http"
    "strings"
    "time"
    
    "github.com/gin-gonic/gin"
    "go.uber.org/zap"
)

type ServiceProxy struct {
    client *http.Client
    logger *zap.Logger
}

func NewServiceProxy(logger *zap.Logger) *ServiceProxy {
    return &ServiceProxy{
        client: &http.Client{
            Timeout: 30 * time.Second,
        },
        logger: logger,
    }
}

func (sp *ServiceProxy) ProxyRequest(targetURL string, timeout time.Duration) gin.HandlerFunc {
    return func(c *gin.Context) {
        // Create context with timeout
        ctx, cancel := context.WithTimeout(c.Request.Context(), timeout)
        defer cancel()
        
        // Build target URL
        targetPath := strings.TrimPrefix(c.Request.URL.Path, "/api/v1")
        fullURL := targetURL + targetPath
        if c.Request.URL.RawQuery != "" {
            fullURL += "?" + c.Request.URL.RawQuery
        }
        
        // Read request body
        var bodyBytes []byte
        var err error
        if c.Request.Body != nil {
            bodyBytes, err = io.ReadAll(c.Request.Body)
            if err != nil {
                sp.logger.Error("Failed to read request body", zap.Error(err))
                c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to read request body"})
                return
            }
        }
        
        // Create new request
        req, err := http.NewRequestWithContext(ctx, c.Request.Method, fullURL, bytes.NewReader(bodyBytes))
        if err != nil {
            sp.logger.Error("Failed to create request", zap.Error(err))
            c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to create request"})
            return
        }
        
        // Copy headers (excluding hop-by-hop headers)
        sp.copyHeaders(c.Request.Header, req.Header)
        
        // Add user context headers if available
        if userID, exists := c.Get("user_id"); exists {
            req.Header.Set("X-User-ID", userID.(string))
        }
        if email, exists := c.Get("email"); exists {
            req.Header.Set("X-User-Email", email.(string))
        }
        if roles, exists := c.Get("roles"); exists {
            if roleSlice, ok := roles.([]string); ok {
                req.Header.Set("X-User-Roles", strings.Join(roleSlice, ","))
            }
        }
        
        // Add request ID for tracing
        if requestID := c.GetHeader("X-Request-ID"); requestID != "" {
            req.Header.Set("X-Request-ID", requestID)
        }
        
        // Make the request
        resp, err := sp.client.Do(req)
        if err != nil {
            sp.logger.Error("Failed to proxy request", 
                zap.String("target", fullURL),
                zap.Error(err))
            
            if ctx.Err() == context.DeadlineExceeded {
                c.JSON(http.StatusGatewayTimeout, gin.H{"error": "Service timeout"})
            } else {
                c.JSON(http.StatusBadGateway, gin.H{"error": "Service unavailable"})
            }
            return
        }
        defer resp.Body.Close()
        
        // Copy response headers
        for key, values := range resp.Header {
            for _, value := range values {
                c.Header(key, value)
            }
        }
        
        // Set status code
        c.Status(resp.StatusCode)
        
        // Copy response body
        if _, err := io.Copy(c.Writer, resp.Body); err != nil {
            sp.logger.Error("Failed to copy response body", zap.Error(err))
        }
        
        // Log the request
        sp.logger.Info("Request proxied",
            zap.String("method", c.Request.Method),
            zap.String("path", c.Request.URL.Path),
            zap.String("target", fullURL),
            zap.Int("status", resp.StatusCode),
            zap.String("client_ip", c.ClientIP()),
        )
    }
}

func (sp *ServiceProxy) copyHeaders(src, dst http.Header) {
    // List of hop-by-hop headers that should not be forwarded
    hopByHopHeaders := map[string]bool{
        "Connection":          true,
        "Keep-Alive":         true,
        "Proxy-Authenticate": true,
        "Proxy-Authorization": true,
        "Te":                 true,
        "Trailers":           true,
        "Transfer-Encoding":  true,
        "Upgrade":            true,
    }
    
    for key, values := range src {
        if !hopByHopHeaders[key] {
            for _, value := range values {
                dst.Add(key, value)
            }
        }
    }
}

// Health check for services
func (sp *ServiceProxy) HealthCheck(serviceURL string) gin.HandlerFunc {
    return func(c *gin.Context) {
        ctx, cancel := context.WithTimeout(c.Request.Context(), 5*time.Second)
        defer cancel()
        
        healthURL := serviceURL + "/health"
        req, err := http.NewRequestWithContext(ctx, "GET", healthURL, nil)
        if err != nil {
            c.JSON(http.StatusInternalServerError, gin.H{
                "service": serviceURL,
                "status":  "error",
                "error":   "Failed to create health check request",
            })
            return
        }
        
        resp, err := sp.client.Do(req)
        if err != nil {
            c.JSON(http.StatusServiceUnavailable, gin.H{
                "service": serviceURL,
                "status":  "unhealthy",
                "error":   err.Error(),
            })
            return
        }
        defer resp.Body.Close()
        
        if resp.StatusCode == http.StatusOK {
            c.JSON(http.StatusOK, gin.H{
                "service": serviceURL,
                "status":  "healthy",
            })
        } else {
            c.JSON(http.StatusServiceUnavailable, gin.H{
                "service": serviceURL,
                "status":  "unhealthy",
                "code":    resp.StatusCode,
            })
        }
    }
}