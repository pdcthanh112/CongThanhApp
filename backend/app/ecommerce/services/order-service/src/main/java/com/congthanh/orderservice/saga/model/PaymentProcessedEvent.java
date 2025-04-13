package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;

public class PaymentProcessedEvent extends SagaEvent{

    private CreateOrderRequest request;

    private Long transactionId;
}
