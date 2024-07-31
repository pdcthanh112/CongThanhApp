package com.congthanh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("product-service", r -> r.path("/product/**").uri("lb://PRODUCT-SERVICE"))
				.route("order-service", r -> r.path("/order/**").uri("lb://ORDER-SERVICE"))
				.route("cart-service", r -> r.path("/cart/**").uri("lb://CART-SERVICE"))
				.route("payment-service", r -> r.path("/payment/**").uri("lb://PAYMENT-SERVICE"))
				.build();
	}
}
