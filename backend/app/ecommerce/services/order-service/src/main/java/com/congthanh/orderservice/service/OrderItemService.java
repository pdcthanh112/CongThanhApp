package com.congthanh.orderservice.service;

import com.congthanh.orderservice.model.dto.OrderItemDTO;
import com.congthanh.orderservice.model.response.ResponseWithPagination;
import com.congthanh.orderservice.model.request.CreateOrderDetailRequest;

public interface OrderItemService {

    OrderItemDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest);

    ResponseWithPagination<OrderItemDTO> getOrderDetailByStatus(String status, int page, int limit);

}
