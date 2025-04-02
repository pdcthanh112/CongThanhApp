package com.congthanh.orderservice.repository.orderItem;

import com.congthanh.orderservice.model.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemCustomRepository {

    Page<OrderItem> findByStatus(String status, Pageable pageable);

}
