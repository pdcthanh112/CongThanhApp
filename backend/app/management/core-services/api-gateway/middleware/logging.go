package middleware

import (
    "bytes"
    "io"
    "time"
    
    "github.com/gin-contrib/requestid"
    "github.com/gin-gonic/gin"
    "go.uber.org/zap"
)

type LoggingMiddleware struct {
    logger *zap.Logger
}

func NewLoggingMiddleware(logger *zap.Logger) *LoggingMiddleware {
    return &LoggingMiddleware{
        logger: logger,
    }
}

type responseWriter struct {
    gin.ResponseWriter
    body *bytes.Buffer
}

func (w *responseWriter) Write(data []byte) (int, error) {
    w.body.Write(data)
    return w.ResponseWriter.Write(data)
}

func (lm *LoggingMiddleware) Middleware() gin.HandlerFunc {
    return gin.LoggerWithFormatter(func(param gin.LogFormatterParams) string {
        // Custom structured logging
        fields := []zap.Field{
            zap.String("method", param.Method),
            zap.String("path", param.Path),
            zap.String("query", param.Request.URL.RawQuery),
            zap.Int("status", param.StatusCode),
            zap.Duration("latency", param.Latency),
            zap.String("client_ip", param.ClientIP),
            zap.String("user_agent", param.Request.UserAgent()),
        }
        
        // Add request ID if available
        if requestID := param.Request.Header.Get("X-Request-ID"); requestID != "" {
            fields = append(fields, zap.String("request_id", requestID))
        }
        
        // Add user ID if available from context
        if userID := param.Keys["user_id"]; userID != nil {
            if uid, ok := userID.(string); ok {
                fields = append(fields, zap.String("user_id", uid))
            }
        }
        
        // Add error information if present
        if len(param.ErrorMessage) > 0 {
            fields = append(fields, zap.String("error", param.ErrorMessage))
        }
        
        // Log based on status code
        if param.StatusCode >= 500 {
            lm.logger.Error("HTTP Request", fields...)
        } else if param.StatusCode >= 400 {
            lm.logger.Warn("HTTP Request", fields...)
        } else {
            lm.logger.Info("HTTP Request", fields...)
        }
        
        return ""
    })
}

func (lm *LoggingMiddleware) DetailedLogging() gin.HandlerFunc {
    return func(c *gin.Context) {
        // Record start time
        start := time.Now()
        
        // Read and log request body for non-GET requests
        var requestBody []byte
        if c.Request.Method != "GET" && c.Request.Body != nil {
            requestBody, _ = io.ReadAll(c.Request.Body)
            c.Request.Body = io.NopCloser(bytes.NewReader(requestBody))
        }
        
        // Wrap response writer to capture response body
        responseBody := &bytes.Buffer{}
        writer := &responseWriter{
            ResponseWriter: c.Writer,
            body:          responseBody,
        }
        c.Writer = writer
        
        // Process request
        c.Next()
        
        // Calculate latency
        latency := time.Since(start)
        
        // Prepare log fields
        fields := []zap.Field{
            zap.String("request_id", requestid.Get(c)),
            zap.String("method", c.Request.Method),
            zap.String("path", c.Request.URL.Path),
            zap.String("query", c.Request.URL.RawQuery),
            zap.Int("status", c.Writer.Status()),
            zap.Duration("latency", latency),
            zap.String("client_ip", c.ClientIP()),
            zap.String("user_agent", c.Request.UserAgent()),
            zap.Int("request_size", len(requestBody)),
            zap.Int("response_size", responseBody.Len()),
        }
        
        // Add user context if available
        if userID, exists := c.Get("user_id"); exists {
            fields = append(fields, zap.String("user_id", userID.(string)))
        }
        if email, exists := c.Get("email"); exists {
            fields = append(fields, zap.String("email", email.(string)))
        }
        
        // Add request headers (selective)
        importantHeaders := []string{
            "Content-Type",
            "Authorization",
            "X-Forwarded-For",
            "X-Real-IP",
        }
        
        headerFields := make(map[string]string)
        for _, header := range importantHeaders {
            if value := c.Request.Header.Get(header); value != "" {
                // Mask sensitive headers
                if header == "Authorization" {
                    headerFields[header] = "Bearer ***"
                } else {
                    headerFields[header] = value
                }
            }
        }
        if len(headerFields) > 0 {
            fields = append(fields, zap.Any("headers", headerFields))
        }
        
        // Log request body for non-GET requests (truncate if too long)
        if len(requestBody) > 0 {
            bodyStr := string(requestBody)
            if len(bodyStr) > 1000 {
                bodyStr = bodyStr[:1000] + "... (truncated)"
            }
            fields = append(fields, zap.String("request_body", bodyStr))
        }
        
        // Log response body for errors (truncate if too long)
        if c.Writer.Status() >= 400 {
            respStr := responseBody.String()
            if len(respStr) > 1000 {
                respStr = respStr[:1000] + "... (truncated)"
            }
            fields = append(fields, zap.String("response_body", respStr))
        }
        
        // Log based on status code
        if c.Writer.Status() >= 500 {
            lm.logger.Error("HTTP Request Details", fields...)
        } else if c.Writer.Status() >= 400 {
            lm.logger.Warn("HTTP Request Details", fields...)
        } else {
            lm.logger.Info("HTTP Request Details", fields...)
        }
    }
}

// SecurityLoggingMiddleware logs security-related events
func (lm *LoggingMiddleware) SecurityLogging() gin.HandlerFunc {
    return func(c *gin.Context) {
        c.Next()
        
        // Log authentication failures
        if c.Writer.Status() == 401 {
            lm.logger.Warn("Authentication failed",
                zap.String("client_ip", c.ClientIP()),
                zap.String("path", c.Request.URL.Path),
                zap.String("user_agent", c.Request.UserAgent()),
                zap.String("request_id", requestid.Get(c)),
            )
        }
        
        // Log authorization failures
        if c.Writer.Status() == 403 {
            userID, _ := c.Get("user_id")
            lm.logger.Warn("Authorization failed",
                zap.String("client_ip", c.ClientIP()),
                zap.String("path", c.Request.URL.Path),
                zap.Any("user_id", userID),
                zap.String("request_id", requestid.Get(c)),
            )
        }
        
        // Log rate limiting
        if c.Writer.Status() == 429 {
            lm.logger.Warn("Rate limit exceeded",
                zap.String("client_ip", c.ClientIP()),
                zap.String("path", c.Request.URL.Path),
                zap.String("request_id", requestid.Get(c)),
            )
        }
    }
}