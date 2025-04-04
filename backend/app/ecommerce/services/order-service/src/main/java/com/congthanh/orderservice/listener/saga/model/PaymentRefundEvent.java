package com.congthanh.orderservice.listener.saga.model;

import java.math.BigDecimal;

public class PaymentRefundEvent extends SagaEvent{

    private Long orderId;

    private BigDecimal amount;
}
