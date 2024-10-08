package com.congthanh.project.service;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.dto.OrderDTO;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

  OrderDTO createOrder(CreateOrderRequest createOrderRequest);

  List<CheckoutDTO> getHistoryByCustomer(String customerId);

}
