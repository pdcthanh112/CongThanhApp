package com.congthanh.catalogservice.config;

import org.springframework.boot.actuate.health.SimpleStatusAggregator;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HealthConfig {

    @Bean
    @Primary
    public StatusAggregator healthStatusAggregator() {
        return new SimpleStatusAggregator();
    }
}
