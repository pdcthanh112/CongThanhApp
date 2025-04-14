package com.congthanh.orderservice.service;

import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.model.viewmodel.OrderDetailVm;
import com.congthanh.orderservice.model.viewmodel.OrderHistoryVm;
import com.congthanh.orderservice.model.viewmodel.OrderVm;

import java.util.List;

public interface OrderService {

  OrderDTO createOrder(CreateOrderRequest createOrderRequest);

  OrderDetailVm getOrderDetailByCode(String orderCode);

  List<OrderHistoryVm> getOrderHistoryByCustomer(String customerId);

}
