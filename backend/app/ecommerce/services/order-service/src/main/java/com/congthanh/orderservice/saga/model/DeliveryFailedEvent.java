package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.OrderSagaRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryFailedEvent extends SagaEvent{

    private String orderId;

    private OrderSagaRequest request;

    private String reason;
}
