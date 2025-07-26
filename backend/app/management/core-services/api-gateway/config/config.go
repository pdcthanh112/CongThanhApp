package config

import (
    "fmt"
    "os"
    "time"
    
    "gopkg.in/yaml.v3"
)

type Config struct {
    Server       ServerConfig                `yaml:"server"`
    Redis        RedisConfig                 `yaml:"redis"`
    JWT          JWTConfig                   `yaml:"jwt"`
    RateLimiting RateLimitingConfig          `yaml:"rate_limiting"`
    Services     map[string]ServiceConfig    `yaml:"services"`
    Routes       []RouteConfig               `yaml:"routes"`
}

type ServerConfig struct {
    Port         int           `yaml:"port"`
    ReadTimeout  time.Duration `yaml:"read_timeout"`
    WriteTimeout time.Duration `yaml:"write_timeout"`
}

type RedisConfig struct {
    Addr     string `yaml:"addr"`
    Password string `yaml:"password"`
    DB       int    `yaml:"db"`
}

type JWTConfig struct {
    Secret    string        `yaml:"secret"`
    ExpiresIn time.Duration `yaml:"expires_in"`
}

type RateLimitingConfig struct {
    RequestsPerMinute int `yaml:"requests_per_minute"`
    Burst            int `yaml:"burst"`
}

type ServiceConfig struct {
    URL            string                `yaml:"url"`
    Timeout        time.Duration         `yaml:"timeout"`
    CircuitBreaker CircuitBreakerConfig  `yaml:"circuit_breaker"`
}

type CircuitBreakerConfig struct {
    MaxRequests uint32        `yaml:"max_requests"`
    Interval    time.Duration `yaml:"interval"`
    Timeout     time.Duration `yaml:"timeout"`
}

type RouteConfig struct {
    Path         string   `yaml:"path"`
    Service      string   `yaml:"service"`
    Methods      []string `yaml:"methods"`
    AuthRequired bool     `yaml:"auth_required"`
    Roles        []string `yaml:"roles"`
}

func LoadConfig(filename string) (*Config, error) {
    data, err := os.ReadFile(filename)
    if err != nil {
        return nil, fmt.Errorf("failed to read config file: %w", err)
    }
    
    var config Config
    if err := yaml.Unmarshal(data, &config); err != nil {
        return nil, fmt.Errorf("failed to unmarshal config: %w", err)
    }
    
    return &config, nil
}