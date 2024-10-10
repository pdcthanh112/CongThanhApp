package com.congthanh.project.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class EnvConfig {

//    @Bean
//    public Dotenv dotenv() {
//        return Dotenv.configure()
////                .directory("C:\\Users\\pdcth\\OneDrive\\Desktop\\CongThanhApp\\backend\\app\\ecommerce\\services\\.env")
////                .filename(".env")
//                .load();
//    }
//
//    @Bean
//    public ConfigurableEnvironment configurableEnvironment(ConfigurableEnvironment environment, Dotenv dotenv) {
//        MutablePropertySources propertySources = environment.getPropertySources();
//        Map<String, String> envMap = dotenv.entries().stream()
//                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
//        propertySources.addFirst(new MapPropertySource("dotenvProperties", (Map) envMap));
//        return environment;
//    }

}
