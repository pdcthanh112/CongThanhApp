package com.congthanh.project.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;

    private final String reason;

}
