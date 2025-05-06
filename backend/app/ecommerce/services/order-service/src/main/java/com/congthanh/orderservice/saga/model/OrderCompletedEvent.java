package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCompletedEvent extends SagaEvent{

    private Long orderId;
}
