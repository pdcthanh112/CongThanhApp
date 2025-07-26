package middleware

import (
    "errors"
    "net/http"
    "sync"
    "time"
    
    "github.com/gin-gonic/gin"
    "go.uber.org/zap"
)

type State int

const (
    StateClosed State = iota
    StateHalfOpen
    StateOpen
)

type CircuitBreaker struct {
    maxRequests uint32
    interval    time.Duration
    timeout     time.Duration
    
    mutex        sync.Mutex
    state        State
    generation   uint64
    counts       *Counts
    expiry       time.Time
    
    logger *zap.Logger
}

type Counts struct {
    Requests             uint32
    TotalSuccesses       uint32
    TotalFailures        uint32
    ConsecutiveSuccesses uint32
    ConsecutiveFailures  uint32
}

func NewCircuitBreaker(maxRequests uint32, interval, timeout time.Duration, logger *zap.Logger) *CircuitBreaker {
    return &CircuitBreaker{
        maxRequests: maxRequests,
        interval:    interval,
        timeout:     timeout,
        state:       StateClosed,
        counts:      &Counts{},
        logger:      logger,
    }
}

func (cb *CircuitBreaker) Middleware(serviceName string) gin.HandlerFunc {
    return func(c *gin.Context) {
        generation, err := cb.beforeRequest()
        if err != nil {
            cb.logger.Warn("Circuit breaker open",
                zap.String("service", serviceName),
                zap.Error(err))
            
            c.JSON(http.StatusServiceUnavailable, gin.H{
                "error":   "Service temporarily unavailable",
                "service": serviceName,
                "reason":  "Circuit breaker is open",
            })
            c.Abort()
            return
        }
        
        // Process the request
        c.Next()
        
        // Check if request was successful
        statusCode := c.Writer.Status()
        success := statusCode >= 200 && statusCode < 500 // 4xx errors are client errors, not service failures
        
        cb.afterRequest(generation, success)
    }
}

func (cb *CircuitBreaker) beforeRequest() (uint64, error) {
    cb.mutex.Lock()
    defer cb.mutex.Unlock()
    
    now := time.Now()
    state, generation := cb.currentState(now)
    
    if state == StateOpen {
        return generation, errors.New("circuit breaker is open")
    } else if state == StateHalfOpen && cb.counts.Requests >= cb.maxRequests {
        return generation, errors.New("too many requests in half-open state")
    }
    
    cb.counts.Requests++
    return generation, nil
}

func (cb *CircuitBreaker) afterRequest(before uint64, success bool) {
    cb.mutex.Lock()
    defer cb.mutex.Unlock()
    
    now := time.Now()
    state, generation := cb.currentState(now)
    if generation != before {
        return
    }
    
    if success {
        cb.onSuccess(state)
    } else {
        cb.onFailure(state)
    }
}

func (cb *CircuitBreaker) onSuccess(state State) {
    cb.counts.TotalSuccesses++
    cb.counts.ConsecutiveSuccesses++
    cb.counts.ConsecutiveFailures = 0
    
    if state == StateHalfOpen {
        cb.setState(StateClosed)
    }
}

func (cb *CircuitBreaker) onFailure(state State) {
    cb.counts.TotalFailures++
    cb.counts.ConsecutiveFailures++
    cb.counts.ConsecutiveSuccesses = 0
    
    if cb.readyToTrip() {
        cb.setState(StateOpen)
    }
}

func (cb *CircuitBreaker) readyToTrip() bool {
    return cb.counts.ConsecutiveFailures >= 5 || 
           (cb.counts.Requests >= 10 && cb.counts.TotalFailures > cb.counts.TotalSuccesses)
}

func (cb *CircuitBreaker) currentState(now time.Time) (State, uint64) {
    switch cb.state {
    case StateClosed:
        if !cb.expiry.IsZero() && cb.expiry.Before(now) {
            cb.toNewGeneration(now)
        }
    case StateOpen:
        if cb.expiry.Before(now) {
            cb.setState(StateHalfOpen)
        }
    }
    
    return cb.state, cb.generation
}

func (cb *CircuitBreaker) setState(state State) {
    if cb.state == state {
        return
    }
    
    prev := cb.state
    cb.state = state
    cb.toNewGeneration(time.Now())
    
    cb.logger.Info("Circuit breaker state changed",
        zap.String("from", stateToString(prev)),
        zap.String("to", stateToString(state)))
}

func (cb *CircuitBreaker) toNewGeneration(now time.Time) {
    cb.generation++
    cb.counts = &Counts{}
    
    var zero time.Time
    switch cb.state {
    case StateClosed:
        if cb.interval == 0 {
            cb.expiry = zero
        } else {
            cb.expiry = now.Add(cb.interval)
        }
    case StateOpen:
        cb.expiry = now.Add(cb.timeout)
    default: // StateHalfOpen
        cb.expiry = zero
    }
}

func stateToString(state State) string {
    switch state {
    case StateClosed:
        return "closed"
    case StateOpen:
        return "open