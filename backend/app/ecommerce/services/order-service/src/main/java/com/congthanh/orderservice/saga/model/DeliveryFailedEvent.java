package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryFailedEvent extends SagaEvent {

    private OrderSagaRequest request;

    private String reason;
}
