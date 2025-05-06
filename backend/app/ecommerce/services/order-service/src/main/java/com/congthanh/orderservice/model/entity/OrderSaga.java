package com.congthanh.orderservice.model.entity;

import com.congthanh.orderservice.saga.model.OrderSagaState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_saga")
public class OrderSaga {

    @Id
    private Long id;

    @Column(name = "saga_id")
    private String sagaId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "current_state")
    private OrderSagaState currentState;
}
