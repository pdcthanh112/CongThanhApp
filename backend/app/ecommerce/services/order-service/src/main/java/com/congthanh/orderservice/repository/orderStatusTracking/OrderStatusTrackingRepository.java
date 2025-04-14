package com.congthanh.orderservice.repository.orderStatusTracking;

import com.congthanh.orderservice.model.entity.OrderStatusTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusTrackingRepository extends JpaRepository<OrderStatusTracking, Long>, OrderStatusTrackingCustomRepository {
}
