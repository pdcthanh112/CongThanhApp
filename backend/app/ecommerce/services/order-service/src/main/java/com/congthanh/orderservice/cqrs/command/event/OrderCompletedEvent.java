package com.congthanh.orderservice.cqrs.command.event;

import lombok.Data;

@Data
public class OrderCompletedEvent {

    private final String orderId;
}
