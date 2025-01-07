package com.congthanh.customerservice.config;

import com.congthanh.customerservice.constant.common.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "customer-exchange";

    @Bean
    public Queue shippingAddressQueue() {
        return new Queue(RabbitMQConstants.ShippingAddress.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding shippingAddressBinding() {
        return BindingBuilder.bind(shippingAddressQueue()).to(exchange()).with(RabbitMQConstants.ShippingAddress.ROUTING_KEY);
    }
}
