package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRefundEvent extends SagaEvent {

    private Long orderId;

    private BigDecimal amount;
}
