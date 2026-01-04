package com.congthanh.orderservice.repository.orderSaga;

import com.congthanh.orderservice.saga.model.OrderSaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSagaRepository extends JpaRepository<OrderSaga, Long> {

    OrderSaga findBySagaId(String sagaId);
}
