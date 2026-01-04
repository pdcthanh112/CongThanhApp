package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.constant.common.OrderSagaState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSagaExecution {

    private boolean success;
    private String sagaId;
    private Long orderId;
    private String errorMessage;
    private OrderSagaState finalState;
    private Instant completedAt;

    public static OrderSagaExecution success(Long orderId) {
        return OrderSagaExecution.builder()
                .success(true)
                .orderId(orderId)
                .finalState(OrderSagaState.COMPLETED)
                .completedAt(Instant.now())
                .build();
    }

    public static OrderSagaExecution failure(String sagaId, String errorMessage) {
        return OrderSagaExecution.builder()
                .success(false)
                .sagaId(sagaId)
                .errorMessage(errorMessage)
                .finalState(OrderSagaState.FAILED)
                .completedAt(Instant.now())
                .build();
    }

    public static OrderSagaExecution compensated(String sagaId, Long orderId, String reason) {
        return OrderSagaExecution.builder()
                .success(false)
                .sagaId(sagaId)
                .orderId(orderId)
                .errorMessage("Order was compensated: " + reason)
                .finalState(OrderSagaState.COMPENSATED)
                .completedAt(Instant.now())
                .build();
    }
}
