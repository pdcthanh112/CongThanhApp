package com.congthanh.orderservice.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CompleteOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;

}
