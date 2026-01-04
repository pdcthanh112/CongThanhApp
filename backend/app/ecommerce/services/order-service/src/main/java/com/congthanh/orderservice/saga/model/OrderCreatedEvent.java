package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreatedEvent extends SagaEvent {

    private OrderSagaRequest request;
}
