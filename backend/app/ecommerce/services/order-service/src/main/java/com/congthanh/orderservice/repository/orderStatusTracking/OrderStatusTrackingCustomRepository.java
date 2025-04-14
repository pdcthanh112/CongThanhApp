package com.congthanh.orderservice.repository.orderStatusTracking;

import com.congthanh.orderservice.model.entity.OrderStatusTracking;

import java.util.List;

public interface OrderStatusTrackingCustomRepository {

    List<OrderStatusTracking> getStatusTrackingByOrderCode(String orderCode);
}
