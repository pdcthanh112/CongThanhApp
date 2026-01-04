package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.request.CreateOrderRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryCreatedEvent extends SagaEvent {

    private CreateOrderRequest orderDTO;

    private String deliveryId;
}
