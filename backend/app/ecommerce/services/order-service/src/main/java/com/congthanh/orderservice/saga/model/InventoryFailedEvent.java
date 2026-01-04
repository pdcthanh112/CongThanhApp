package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryFailedEvent extends SagaEvent {

    private OrderSagaRequest request;
}
