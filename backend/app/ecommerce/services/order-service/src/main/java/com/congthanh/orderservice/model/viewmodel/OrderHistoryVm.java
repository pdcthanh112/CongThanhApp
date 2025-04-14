package com.congthanh.orderservice.model.viewmodel;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.constant.enums.PaymentStatus;
import com.congthanh.orderservice.constant.enums.ShippingStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderHistoryVm(
        Long id,
        String orderCode,
        String customer,
        String note,
        OrderStatus status,
        PaymentStatus paymentStatus,
        ShippingStatus shippingStatus,
        List<OrderItemHistory> orderItems,
        Instant orderDate
) {
}
