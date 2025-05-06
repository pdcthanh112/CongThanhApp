package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.OrderSagaRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryFailedEvent extends SagaEvent{

    private Long orderId;

    private OrderSagaRequest request;
}
