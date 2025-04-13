package com.congthanh.orderservice.saga.model;

public class NotificationEvent extends SagaEvent{
    private String type;
    private String recipientId;
    private String message;
}
