package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class DeliveryCreatedEvent extends SagaEvent{
    private String orderId;
    private CreateOrderRequest orderDTO;
    private String deliveryId;
}
