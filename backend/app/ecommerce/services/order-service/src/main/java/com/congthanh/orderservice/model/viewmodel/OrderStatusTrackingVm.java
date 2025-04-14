package com.congthanh.orderservice.model.viewmodel;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderStatusTrackingVm(
        Long id,
        OrderStatus status,
        int stepOrder,
        String description,
        Instant changedAt
) {
}
