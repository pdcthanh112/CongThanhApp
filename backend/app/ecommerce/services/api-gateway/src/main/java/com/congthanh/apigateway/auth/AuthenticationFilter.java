package com.congthanh.apigateway.auth;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Lấy header Authorization
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid token");
            }
            // Xác thực token (gọi tới auth server)
            return chain.filter(exchange);
        };
    }

    public static class Config {}
}
