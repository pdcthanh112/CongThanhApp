package middleware

import (
    "context"
    "fmt"
    "net/http"
    "strconv"
    "time"
    
    "github.com/gin-gonic/gin"
    "github.com/go-redis/redis/v8"
    "golang.org/x/time/rate"
)

type RateLimiter struct {
    redis            *redis.Client
    requestsPerMinute int
    burst            int
}

func NewRateLimiter(redisClient *redis.Client, requestsPerMinute, burst int) *RateLimiter {
    return &RateLimiter{
        redis:            redisClient,
        requestsPerMinute: requestsPerMinute,
        burst:            burst,
    }
}

func (rl *RateLimiter) Middleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        // Get client identifier (IP address or user ID if authenticated)
        clientID := c.ClientIP()
        if userID, exists := c.Get("user_id"); exists {
            clientID = fmt.Sprintf("user:%s", userID)
        }
        
        allowed, err := rl.isAllowed(c.Request.Context(), clientID)
        if err != nil {
            c.JSON(http.StatusInternalServerError, gin.H{"error": "Rate limiting error"})
            c.Abort()
            return
        }
        
        if !allowed {
            c.Header("Retry-After", "60") // Suggest retry after 60 seconds
            c.JSON(http.StatusTooManyRequests, gin.H{
                "error": "Rate limit exceeded",
                "limit": rl.requestsPerMinute,
                "window": "1 minute",
            })
            c.Abort()
            return
        }
        
        c.Next()
    }
}

func (rl *RateLimiter) isAllowed(ctx context.Context, clientID string) (bool, error) {
    key := fmt.Sprintf("rate_limit:%s", clientID)
    now := time.Now()
    window := now.Truncate(time.Minute)
    
    pipe := rl.redis.Pipeline()
    
    // Increment counter for current window
    incr := pipe.Incr(ctx, key+":"+strconv.FormatInt(window.Unix(), 10))
    
    // Set expiration for the key (2 minutes to handle edge cases)
    pipe.Expire(ctx, key+":"+strconv.FormatInt(window.Unix(), 10), 2*time.Minute)
    
    _, err := pipe.Exec(ctx)
    if err != nil {
        return false, err
    }
    
    count, err := incr.Result()
    if err != nil {
        return false, err
    }
    
    return count <= int64(rl.requestsPerMinute), nil
}

// Token bucket rate limiter for more sophisticated rate limiting
type TokenBucketLimiter struct {
    limiters map[string]*rate.Limiter
    redis    *redis.Client
    rate     rate.Limit
    burst    int
}

func NewTokenBucketLimiter(redisClient *redis.Client, requestsPerSecond float64, burst int) *TokenBucketLimiter {
    return &TokenBucketLimiter{
        limiters: make(map[string]*rate.Limiter),
        redis:    redisClient,
        rate:     rate.Limit(requestsPerSecond),
        burst:    burst,
    }
}

func (tbl *TokenBucketLimiter) getLimiter(clientID string) *rate.Limiter {
    limiter, exists := tbl.limiters[clientID]
    if !exists {
        limiter = rate.NewLimiter(tbl.rate, tbl.burst)
        tbl.limiters[clientID] = limiter
    }
    return limiter
}

func (tbl *TokenBucketLimiter) Middleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        clientID := c.ClientIP()
        if userID, exists := c.Get("user_id"); exists {
            clientID = fmt.Sprintf("user:%s", userID)
        }
        
        limiter := tbl.getLimiter(clientID)
        
        if !limiter.Allow() {
            c.Header("Retry-After", "1")
            c.JSON(http.StatusTooManyRequests, gin.H{
                "error": "Rate limit exceeded",
                "retry_after": "1 second",
            })
            c.Abort()
            return
        }
        
        c.Next()
    }
}