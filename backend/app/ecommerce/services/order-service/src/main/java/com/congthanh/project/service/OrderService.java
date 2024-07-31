package com.congthanh.project.service;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.entity.ecommerce.Order;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

  Order createOrder(CreateOrderRequest createOrderRequest);

  List<CheckoutDTO> getHistoryByCustomer(String customerId);

}
