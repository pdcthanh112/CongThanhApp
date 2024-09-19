package com.congthanh.project.cqrs.command.service;

import com.congthanh.project.cqrs.command.command.CreateProductCommand;
import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.entity.Product;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final CommandGateway commandGateway;

    private final AmqpTemplate rabbitTemplate;

    @Transactional
    public String createProduct(String name, String description) {
        String productId = UUID.randomUUID().toString();
        CreateProductCommand command = CreateProductCommand.builder()
                .name(name)
                .description(description)
                .build();
        commandGateway.send(command);
        return productId;
    }

    private void publishProductCreatedEvent(Product product) {
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();

        rabbitTemplate.convertAndSend("productExchange", "product.created", event);
    }
}
