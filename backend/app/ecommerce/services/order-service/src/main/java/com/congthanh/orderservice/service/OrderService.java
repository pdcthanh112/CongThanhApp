package com.congthanh.orderservice.service;

import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

  OrderDTO createOrder(CreateOrderRequest createOrderRequest);

  List<CheckoutDTO> getHistoryByCustomer(String customerId);

}
