package com.congthanh.project.service;

import com.congthanh.project.dto.OrderDetailDTO;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.model.request.CreateOrderDetailRequest;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest);

    ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit);

}
