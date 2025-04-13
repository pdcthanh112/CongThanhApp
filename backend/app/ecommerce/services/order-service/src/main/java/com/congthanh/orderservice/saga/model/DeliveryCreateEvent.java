package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class DeliveryCreateEvent extends SagaEvent{

    private String orderId;
    private CreateOrderRequest request;
    private Object deliveryAddress;
}
