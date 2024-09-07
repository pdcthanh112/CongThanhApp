package com.congthanh.project.cqrs.command.event;

import lombok.Data;

@Data
public class PaymentFailedEvent {

    private final String id;

    private final String orderId;

    private final String reason;

}
