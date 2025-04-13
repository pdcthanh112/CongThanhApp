package com.congthanh.orderservice.model.viewmodel;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.constant.enums.PaymentStatus;
import com.congthanh.orderservice.constant.enums.ShippingStatus;
import com.congthanh.orderservice.model.entity.OrderItem;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderVm(
        Long id,
        String orderNumber,
        String customer,
        String note,
        OrderStatus status,
        PaymentStatus paymentStatus,
        ShippingStatus shippingStatus,
        List<OrderItem> orderItems,
        Instant orderDate
) {
}
