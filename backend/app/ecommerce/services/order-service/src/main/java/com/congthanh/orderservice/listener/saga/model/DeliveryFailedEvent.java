package com.congthanh.orderservice.listener.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class DeliveryFailedEvent extends SagaEvent{
    private String orderId;
    private CreateOrderRequest request;
    private String reason;
}
