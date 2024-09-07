package com.congthanh.project.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String name;

    private final double price;

}
