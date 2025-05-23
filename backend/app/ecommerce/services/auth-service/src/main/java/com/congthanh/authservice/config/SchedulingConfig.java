package com.congthanh.authservice.config;

import com.congthanh.authservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private JwtService jwtService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every 24 hours
    public void cleanExpiredTokens() {
        jwtService.cleanExpiredTokens();
    }
}
