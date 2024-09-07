package com.congthanh.project.cqrs.command.event;

import lombok.Data;

@Data
public class OrderCompletedEvent {

    private final String orderId;
}
