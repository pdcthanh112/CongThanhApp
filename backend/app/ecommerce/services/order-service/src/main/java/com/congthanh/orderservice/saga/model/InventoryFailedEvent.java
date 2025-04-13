package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class InventoryFailedEvent extends SagaEvent{

    private Long orderId;

    private CreateOrderRequest request;
}
