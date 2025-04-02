package com.congthanh.orderservice.repository.orderItem;

import com.congthanh.orderservice.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String>, OrderItemCustomRepository {
}
