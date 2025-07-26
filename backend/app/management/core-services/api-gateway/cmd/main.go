package main

import (
    "context"
    "fmt"
    "net/http"
    "os"
    "os/signal"
    "path/filepath"
    "strings"
    "syscall"
    "time"
    
    "github.com/gin-contrib/cors"
    "github.com/gin-contrib/requestid"
    "github.com/gin-gonic/gin"
    "github.com/go-redis/redis/v8"
    "github.com/prometheus/client_golang/prometheus/promhttp"
    "go.uber.org/zap"
    
    "api-gateway/config"
    "api-gateway/middleware"
    "api-gateway/monitoring"
    "api-gateway/proxy"
)

func main() {
    // Initialize logger
    logger, err := zap.NewProduction()
    if err != nil {
        panic(fmt.Sprintf("Failed to initialize logger: %v", err))
    }
    defer logger.Sync()
    
    // Load configuration
    cfg, err := config.LoadConfig("config.yaml")
    if err != nil {
        logger.Fatal("Failed to load configuration", zap.Error(err))
    }
    
    // Initialize Redis client
    redisClient := redis.NewClient(&redis.Options{
        Addr:     cfg.Redis.Addr,
        Password: cfg.Redis.Password,
        DB:       cfg.Redis.DB,
    })
    
    // Test Redis connection
    ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
    defer cancel()
    
    if err := redisClient.Ping(ctx).Err(); err != nil {
        logger.Fatal("Failed to connect to Redis", zap.Error(err))
    }
    
    // Initialize components
    authMiddleware := middleware.NewAuthMiddleware(cfg.JWT.Secret, redisClient)
    rateLimiter := middleware.NewRateLimiter(redisClient, cfg.RateLimiting.RequestsPerMinute, cfg.RateLimiting.Burst)
    loggingMiddleware := middleware.NewLoggingMiddleware(logger)
    metricsMiddleware := monitoring.NewMetricsMiddleware()
    serviceProxy := proxy.NewServiceProxy(logger)
    
    // Initialize circuit breakers for each service
    circuitBreakers := make(map[string]*middleware.CircuitBreaker)
    for serviceName, serviceConfig := range cfg.Services {
        circuitBreakers[serviceName] = middleware.NewCircuitBreaker(
            serviceConfig.CircuitBreaker.MaxRequests,
            serviceConfig.CircuitBreaker.Interval,
            serviceConfig.CircuitBreaker.Timeout,
            logger,
        )
    }
    
    // Setup Gin router
    if os.Getenv("GIN_MODE") == "" {
        gin.SetMode(gin.ReleaseMode)
    }
    
    router := gin.New()
    
    // Global middleware
    router.Use(gin.Recovery())
    router.Use(requestid.New())
    router.Use(loggingMiddleware.Middleware())
    router.Use(loggingMiddleware.SecurityLogging())
    router.Use(metricsMiddleware.Middleware())
    
    // CORS configuration
    router.Use(cors.New(cors.Config{
        AllowOrigins:     []string{"*"}, // Configure based on your frontend domains
        AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
        AllowHeaders:     []string{"Origin", "Content-Type", "Authorization", "X-Request-ID"},
        ExposeHeaders:    []string{"X-Request-ID"},
        AllowCredentials: true,
        MaxAge:           12 * time.Hour,
    }))
    
    // Health check endpoint
    router.GET("/health", func(c *gin.Context) {
        c.JSON(http.StatusOK, gin.H{
            "status":    "healthy",
            "timestamp": time.Now().UTC(),
            "version":   "1.0.0",
        })
    })
    
    // Metrics endpoint for Prometheus
    router.GET("/metrics", gin.WrapH(promhttp.Handler()))
    
    // Service health checks
    healthGroup := router.Group("/health")
    {
        for serviceName, serviceConfig := range cfg.Services {
            healthGroup.GET("/"+serviceName, serviceProxy.HealthCheck(serviceConfig.URL))
        }
    }
    
    // API routes
    apiV1 := router.Group("/api/v1")
    
    // Apply rate limiting to all API routes
    apiV1.Use(rateLimiter.Middleware())
    
    // Setup routes based on configuration
    for _, route := range cfg.Routes {
        serviceConfig, exists := cfg.Services[route.Service]
        if !exists {
            logger.Error("Service not found in configuration", zap.String("service", route.Service))
            continue
        }
        
        // Create route group
        routeGroup := apiV1.Group("")
        
        // Apply circuit breaker
        if cb, exists := circuitBreakers[route.Service]; exists {
            routeGroup.Use(cb.Middleware(route.Service))
        }
        
        // Apply authentication if required
        if route.AuthRequired {
            routeGroup.Use(authMiddleware.ValidateToken())
            
            // Apply role-based authorization if roles are specified
            if len(route.Roles) > 0 {
                routeGroup.Use(authMiddleware.RequireRoles(route.Roles))
            }
        }
        
        // Setup route handlers
        for _, method := range route.Methods {
            switch strings.ToUpper(method) {
            case "GET":
                routeGroup.GET(route.Path, serviceProxy.ProxyRequest(serviceConfig.URL, serviceConfig.Timeout))
            case "POST":
                routeGroup.POST(route.Path, serviceProxy.ProxyRequest(serviceConfig.URL, serviceConfig.Timeout))
            case "PUT":
                routeGroup.PUT(route.Path, serviceProxy.ProxyRequest(serviceConfig.URL, serviceConfig.Timeout))
            case "DELETE":
                routeGroup.DELETE(route.Path, serviceProxy.ProxyRequest(serviceConfig.URL, serviceConfig.Timeout))
            case "PATCH":
                routeGroup.PATCH(route.Path, serviceProxy.ProxyRequest(serviceConfig.URL, serviceConfig.Timeout))
            }
        }
        
        logger.Info("Route configured",
            zap.String("path", route.Path),
            zap.String("service", route.Service),
            zap.Strings("methods", route.Methods),
            zap.Bool("auth_required", route.AuthRequired),
            zap.Strings("roles", route.Roles),
        )
    }
    
    // Admin routes (with authentication)
    adminGroup := router.Group("/admin")
    adminGroup.Use(authMiddleware.ValidateToken())
    adminGroup.Use(authMiddleware.RequireRoles([]string{"admin"}))
    {
        // Circuit breaker status endpoint
        adminGroup.GET("/circuit-breakers", func(c *gin.Context) {
            status := make(map[string]interface{})
            for serviceName, cb := range circuitBreakers {
                // You would need to add methods to get circuit breaker status
                status[serviceName] = map[string]interface{}{
                    "state": "closed", // This would be actual state
                }
            }
            c.JSON(http.StatusOK, status)
        })
        
        // Configuration endpoint
        adminGroup.GET("/config", func(c *gin.Context) {
            c.JSON(http.StatusOK, cfg)
        })
        
        // Clear rate limits (emergency endpoint)
        adminGroup.POST("/rate-limits/clear", func(c *gin.Context) {
            // Implementation to clear rate limits in Redis
            c.JSON(http.StatusOK, gin.H{"message": "Rate limits cleared"})
        })
    }
    
    // Create HTTP server
    server := &http.Server{
        Addr:         fmt.Sprintf(":%d", cfg.Server.Port),
        Handler:      router,
        ReadTimeout:  cfg.Server.ReadTimeout,
        WriteTimeout: cfg.Server.WriteTimeout,
    }
    
    // Start server in a goroutine
    go func() {
        logger.Info("Starting API Gateway",
            zap.Int("port", cfg.Server.Port),
            zap.Duration("read_timeout", cfg.Server.ReadTimeout),
            zap.Duration("write_timeout", cfg.Server.WriteTimeout),
        )
        
        if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
            logger.Fatal("Failed to start server", zap.Error(err))
        }
    }()
    
    // Wait for interrupt signal to gracefully shutdown
    quit := make(chan os.Signal, 1)
    signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
    <-quit
    
    logger.Info("Shutting down API Gateway...")
    
    // Graceful shutdown with timeout
    ctx, cancel = context.WithTimeout(context.Background(), 30*time.Second)
    defer cancel()
    
    if err := server.Shutdown(ctx); err != nil {
        logger.Error("Server forced to shutdown", zap.Error(err))
    }
    
    // Close Redis connection
    if err := redisClient.Close(); err != nil {
        logger.Error("Error closing Redis connection", zap.Error(err))
    }
    
    logger.Info("API Gateway stopped")
}

// Helper function to get config file path
func getConfigPath() string {
    if path := os.Getenv("CONFIG_PATH"); path != "" {
        return path
    }
    
    // Try to find config.yaml in common locations
    locations := []string{
        "config.yaml",
        "configs/config.yaml",
        "/etc/api-gateway/config.yaml",
    }
    
    for _, location := range locations {
        if _, err := os.Stat(location); err == nil {
            return location
        }
    }
    
    return "config.yaml"
}