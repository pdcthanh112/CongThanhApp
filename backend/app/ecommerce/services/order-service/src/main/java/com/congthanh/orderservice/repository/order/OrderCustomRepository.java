package com.congthanh.orderservice.repository.order;

import com.congthanh.orderservice.model.entity.Order;

import java.util.List;

public interface OrderCustomRepository {

    List<Order> getOrderHistoryByCustomer(String customerId);
}
