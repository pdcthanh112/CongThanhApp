package com.congthanh.orderservice.cqrs.command.event;

import lombok.Data;

@Data
public class OrderCanceledEvent {

    private final String orderId;

    private final String reason;

}
