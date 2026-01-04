package com.congthanh.orderservice.constant.common;

public enum OrderSagaState {
    STARTED,
    PRODUCT_VALIDATED,
    INVENTORY_RESERVED,
    PAYMENT_PROCESSING,
    PAYMENT_COMPLETED,
    SHIPPING_CREATED,
    NOTIFICATION_SENT,
    COMPLETED,
    FAILED,
    COMPENSATING,
    COMPENSATED
}
