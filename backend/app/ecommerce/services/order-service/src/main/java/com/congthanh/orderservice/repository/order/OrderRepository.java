package com.congthanh.orderservice.repository.order;

import com.congthanh.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, OrderCustomRepository {

    Optional<Order> findByOrderCode(String orderCode);
}
