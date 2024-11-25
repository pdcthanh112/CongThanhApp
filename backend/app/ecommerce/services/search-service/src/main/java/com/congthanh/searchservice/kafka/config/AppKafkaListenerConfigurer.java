package com.congthanh.searchservice.kafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableKafka
public class AppKafkaListenerConfigurer implements KafkaListenerConfigurer {

    private final LocalValidatorFactoryBean validator;

    public AppKafkaListenerConfigurer(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        // Enable message validation
        registrar.setValidator(this.validator);
    }

}
