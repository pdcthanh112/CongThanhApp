package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent extends SagaEvent{
    private String type;
    private String recipientId;
    private String message;
}
