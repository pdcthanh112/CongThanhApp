package com.congthanh.project.service;

import com.congthanh.project.dto.ecommerce.OrderDetailDTO;
import com.congthanh.project.model.ecommerce.request.CreateOrderDetailRequest;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;

public interface OrderDetailService {

    OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest);

    ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit);

}
