package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class InventoryUpdatedEvent extends SagaEvent{

    private Long orderId;

    private CreateOrderRequest request;
}
