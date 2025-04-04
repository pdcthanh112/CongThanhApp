package com.congthanh.orderservice.listener.saga.model;

public class OrderCancelEvent extends SagaEvent{

    private Long orderId;

    private String reason;
}
