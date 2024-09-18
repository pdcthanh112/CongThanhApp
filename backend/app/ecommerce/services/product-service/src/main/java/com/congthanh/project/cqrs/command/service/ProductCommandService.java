package com.congthanh.project.cqrs.command.service;

import com.congthanh.project.cqrs.command.command.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final CommandGateway commandGateway;

    public String createProduct(String name, String description) {
        String productId = UUID.randomUUID().toString();
        CreateProductCommand command = CreateProductCommand.builder()
                .name(name)
                .description(description)
                .build();
        commandGateway.send(command);
        return productId;
    }

}
