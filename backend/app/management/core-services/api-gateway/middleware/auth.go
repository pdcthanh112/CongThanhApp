package middleware

import (
    "net/http"
    "strings"
    "time"
    
    "github.com/gin-gonic/gin"
    "github.com/golang-jwt/jwt/v5"
    "github.com/go-redis/redis/v8"
)

type Claims struct {
    UserID string   `json:"user_id"`
    Email  string   `json:"email"`
    Roles  []string `json:"roles"`
    jwt.RegisteredClaims
}

type AuthMiddleware struct {
    jwtSecret []byte
    redis     *redis.Client
}

func NewAuthMiddleware(jwtSecret string, redisClient *redis.Client) *AuthMiddleware {
    return &AuthMiddleware{
        jwtSecret: []byte(jwtSecret),
        redis:     redisClient,
    }
}

func (a *AuthMiddleware) ValidateToken() gin.HandlerFunc {
    return func(c *gin.Context) {
        authHeader := c.GetHeader("Authorization")
        if authHeader == "" {
            c.JSON(http.StatusUnauthorized, gin.H{"error": "Authorization header required"})
            c.Abort()
            return
        }
        
        tokenString := strings.TrimPrefix(authHeader, "Bearer ")
        if tokenString == authHeader {
            c.JSON(http.StatusUnauthorized, gin.H{"error": "Bearer token required"})
            c.Abort()
            return
        }
        
        // Check if token is blacklisted
        isBlacklisted, err := a.redis.Get(c.Request.Context(), "blacklist:"+tokenString).Result()
        if err == nil && isBlacklisted == "true" {
            c.JSON(http.StatusUnauthorized, gin.H{"error": "Token has been revoked"})
            c.Abort()
            return
        }
        
        claims := &Claims{}
        token, err := jwt.ParseWithClaims(tokenString, claims, func(token *jwt.Token) (interface{}, error) {
            return a.jwtSecret, nil
        })
        
        if err != nil || !token.Valid {
            c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid token"})
            c.Abort()
            return
        }
        
        // Check if token is expired
        if claims.ExpiresAt.Time.Before(time.Now()) {
            c.JSON(http.StatusUnauthorized, gin.H{"error": "Token has expired"})
            c.Abort()
            return
        }
        
        // Set user info in context
        c.Set("user_id", claims.UserID)
        c.Set("email", claims.Email)
        c.Set("roles", claims.Roles)
        c.Set("token", tokenString)
        
        c.Next()
    }
}

func (a *AuthMiddleware) RequireRoles(requiredRoles []string) gin.HandlerFunc {
    return func(c *gin.Context) {
        if len(requiredRoles) == 0 {
            c.Next()
            return
        }
        
        userRoles, exists := c.Get("roles")
        if !exists {
            c.JSON(http.StatusForbidden, gin.H{"error": "No roles found in token"})
            c.Abort()
            return
        }
        
        roles, ok := userRoles.([]string)
        if !ok {
            c.JSON(http.StatusForbidden, gin.H{"error": "Invalid roles format"})
            c.Abort()
            return
        }
        
        hasRequiredRole := false
        for _, required := range requiredRoles {
            for _, userRole := range roles {
                if userRole == required {
                    hasRequiredRole = true
                    break
                }
            }
            if hasRequiredRole {
                break
            }
        }
        
        if !hasRequiredRole {
            c.JSON(http.StatusForbidden, gin.H{
                "error": "Insufficient permissions",
                "required_roles": requiredRoles,
            })
            c.Abort()
            return
        }
        
        c.Next()
    }
}