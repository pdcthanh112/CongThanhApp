package com.congthanh.project.cqrs.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {

    private final String id;
    private final String name;
    private final double price;

}
