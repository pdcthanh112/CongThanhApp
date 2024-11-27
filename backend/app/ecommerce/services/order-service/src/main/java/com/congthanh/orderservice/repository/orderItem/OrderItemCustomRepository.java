package com.congthanh.orderservice.repository.orderItem;

import com.congthanh.orderservice.model.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderItemCustomRepository {

    Page<OrderItem> findByStatus(String status, Pageable pageable);

}
