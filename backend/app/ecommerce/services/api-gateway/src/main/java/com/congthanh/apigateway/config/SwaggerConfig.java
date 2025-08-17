package com.congthanh.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

//    @Bean
//    @Primary
//    public SwaggerUiConfigParameters swaggerUiConfigParameters() {
//        return SwaggerUiConfigParametersBuilder.builder()
//                .configUrl("/v3/api-docs/swagger-config")
//                .build();
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservices API Gateway")
                        .version("1.0.0")
                        .description("Aggregated API documentation for all microservices"));
    }

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();

        groups.add(GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/api/users/**")
                .build());

        groups.add(GroupedOpenApi.builder()
                .group("product-service")
                .pathsToMatch("/api/products/**")
                .build());

        groups.add(GroupedOpenApi.builder()
                .group("order-service")
                .pathsToMatch("/api/orders/**")
                .build());

        return groups;
    }
}
