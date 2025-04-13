package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class PaymentFailedEvent extends SagaEvent{

    private CreateOrderRequest request;
}
