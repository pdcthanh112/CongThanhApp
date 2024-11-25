package com.congthanh.paymentservice.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class ProcessPaymentCommand {

    @TargetAggregateIdentifier
    private final String paymentId;

    private final String orderId;

    private final BigDecimal amount;

}
