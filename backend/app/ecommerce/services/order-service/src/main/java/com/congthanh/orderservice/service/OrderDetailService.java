package com.congthanh.orderservice.service;

import com.congthanh.orderservice.model.dto.OrderDetailDTO;
import com.congthanh.orderservice.model.response.ResponseWithPagination;
import com.congthanh.orderservice.model.request.CreateOrderDetailRequest;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest);

    ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit);

}
