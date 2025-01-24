package com.congthanh.taxservice.config;

import com.congthanh.catalogservice.constant.common.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "catalog_service_exchange";

    @Bean
    public Queue categoryQueue() {
        return new Queue(RabbitMQConstants.Category.QUEUE_NAME, false);
    }

    @Bean
    public Queue tagQueue() {
        return new Queue(RabbitMQConstants.Tag.QUEUE_NAME, false);
    }

    @Bean
    public Queue brandQueue() {
        return new Queue(RabbitMQConstants.Brand.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding categoryBinding() {
        return BindingBuilder.bind(categoryQueue()).to(exchange()).with(RabbitMQConstants.Category.ROUTING_KEY);
    }

    @Bean
    public Binding tagBinding() {
        return BindingBuilder.bind(tagQueue()).to(exchange()).with(RabbitMQConstants.Tag.ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        classMapper.setTrustedPackages("*", "**", "com.congthanh.project.**", "com.congthanh.project.entity.*");
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

}
