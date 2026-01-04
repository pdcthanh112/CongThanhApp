package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryUpdatedEvent extends SagaEvent {

    private OrderSagaRequest request;
}
