package com.congthanh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("product-service", r -> r.path("/products/**")
						.uri("lb://PRODUCT-SERVICE"))
				.route("order-service", r -> r.path("/orders/**")
						.uri("lb://ORDER-SERVICE"))
				.route("cart-service", r -> r.path("/carts/**")
						.uri("lb://CART-SERVICE"))
				.route("payment-service", r -> r.path("/payments/**")
						.uri("lb://PAYMENT-SERVICE"))
				.build();
	}
}
