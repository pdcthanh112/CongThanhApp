package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.entity.Order;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

  Order createOrder(CreateOrderRequest createOrderRequest);

  List<CheckoutDTO> getHistoryByCustomer(String customerId);

}
