package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.CheckoutDTO;
import com.congthanh.project.dto.ecommerce.OrderDTO;
import com.congthanh.project.entity.ecommerce.Order;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;

import java.util.List;

public interface OrderService {

  Order createOrder(CreateOrderRequest createOrderRequest);

  List<CheckoutDTO> getHistoryByCustomer(String customerId);

}
