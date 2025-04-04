package com.congthanh.orderservice.listener.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class PaymentFailedEvent extends SagaEvent{

    private CreateOrderRequest request;
}
