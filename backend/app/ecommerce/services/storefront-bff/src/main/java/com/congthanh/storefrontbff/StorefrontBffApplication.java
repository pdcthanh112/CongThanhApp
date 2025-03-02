package com.congthanh.storefrontbff;

import com.congthanh.storefrontbff.config.ServiceUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableConfigurationProperties(ServiceUrlConfig.class)
public class StorefrontBffApplication {

	// TODO remove this bean after https://github.com/spring-projects/spring-security/issues/15989#issuecomment-2442660753 is fixed
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	WebFilter writeableHeaders() {
		return (exchange, chain) -> {
			HttpHeaders writeableHeaders = HttpHeaders.writableHttpHeaders(
					exchange.getRequest().getHeaders());
			ServerHttpRequestDecorator writeableRequest = new ServerHttpRequestDecorator(
					exchange.getRequest()) {
				@Override
				public HttpHeaders getHeaders() {
					return writeableHeaders;
				}
			};
			ServerWebExchange writeableExchange = exchange.mutate()
					.request(writeableRequest)
					.build();
			return chain.filter(writeableExchange);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StorefrontBffApplication.class, args);
	}

}
