package com.congthanh.project.service;

import com.congthanh.project.dto.OrderDetailDTO;
import com.congthanh.project.model.request.CreateOrderDetailRequest;
import com.congthanh.project.model.response.ResponseWithPagination;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest);

    ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit);

}
