package com.congthanh.orderservice.model.viewmodel;

import com.congthanh.orderservice.constant.enums.OrderStatus;

import java.time.Instant;

public record StatusTracking(
        Long id,
        OrderStatus status,
        int stepOrder,
        String description,
        Instant changedAt
) {
}
