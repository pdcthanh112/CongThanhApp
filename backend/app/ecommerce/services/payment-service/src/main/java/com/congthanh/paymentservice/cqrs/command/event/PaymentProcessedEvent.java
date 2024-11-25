package com.congthanh.paymentservice.cqrs.command.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentProcessedEvent {

    private final String id;

    private final String orderId;

    private final BigDecimal amount;

}
