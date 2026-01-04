package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.constant.common.OrderSagaState;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

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
    @Enumerated(EnumType.STRING)
    private OrderSagaState state;

    @Column(columnDefinition = "jsonb")
//    @Type(type = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter=CreateOrderRequest.class)
    private CreateOrderRequest request;

    private String inventoryReservationId;
    private String paymentId;
    private String shippingId;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter=Object.class)
    private Object productValidationData;

    private String failureReason;
    private String compensationError;

    private Instant createdAt;
    private Instant updatedAt;

    @Version
    private Long version;
}
