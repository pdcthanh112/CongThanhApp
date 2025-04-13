package com.congthanh.orderservice.saga.model;

public class OrderCancelEvent extends SagaEvent{

    private Long orderId;

    private String reason;
}
