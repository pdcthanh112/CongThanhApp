//package com.congthanh.apigateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
////                .route("auth_route", r -> r.path("/auth/**")
////                        .filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/${segment}"))
////                        .uri("lb://auth-service"))
//                .route("cart_route", r -> r.path("/cart/**")
//                        .uri("lb://cart-service"))
//                .route("catalog_route", r -> r.path("/catalog/**")
//                        .uri("lb://catalog-service"))
////                .route("catalog_route", r -> r.path("/api/ecommerce/catalog/**",
////                                "/api/ecommerce/category/**",
////                                "/api/ecommerce/subcategory/**",
////                                "/api/ecommerce/product-brand/**",
////                                "/api/ecommerce/product-tag/**")
////                        .filters(f -> f.rewritePath("/api/ecommerce/(?<segment>.*)", "/${segment}"))
////                        .uri("lb://catalog-service"))
//                .route("customer_route", r -> r.path("/customer/**")
//                        .uri("lb://customer-service"))
//                .route("inventory_route", r -> r.path("/inventory/**")
//                        .uri("lb://inventory-service"))
//                .route("notification_route", r -> r.path("/notification/**")
//                        .uri("lb://notification-service"))
//                .route("order_route", r -> r.path("/order/**")
//                        .uri("lb://order-service"))
//                .route("payment_route", r -> r.path("/payment/**")
//                        .uri("lb://payment-service"))
//                .route("product_route", r -> r.path("/product/**")
//                        .uri("lb://product-service"))
//                .route("promotion_route", r -> r.path("/promotion/**")
//                        .uri("lb://promotion-service"))
//                .build();
//    }
//
//}
