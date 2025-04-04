package com.congthanh.orderservice.listener.saga.model;

public class NotificationEvent extends SagaEvent{
    private String type;
    private String recipientId;
    private String message;
}
