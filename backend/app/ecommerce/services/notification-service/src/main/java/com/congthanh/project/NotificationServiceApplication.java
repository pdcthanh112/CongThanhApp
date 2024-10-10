package com.congthanh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(NotificationServiceApplication.class, args);
        Environment env = context.getEnvironment();

        String[] activeProfiles = env.getActiveProfiles();
        System.out.println("Active profiles: " + String.join(", ", activeProfiles));
        System.out.println("SERVER_PORT: " + env.getProperty("SERVER_PORT"));
        System.out.println("JWT_SECRET_KEY: " + env.getProperty("JWT_SECRET_KEY"));

        SpringApplication.run(NotificationServiceApplication.class, args);
    }

}
