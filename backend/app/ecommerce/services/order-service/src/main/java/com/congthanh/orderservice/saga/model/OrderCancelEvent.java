package com.congthanh.orderservice.saga.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderCancelEvent extends SagaEvent{

    private Long orderId;

    private String reason;
}
