package com.congthanh.orderservice.saga.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public abstract class SagaEvent implements Serializable {

    private String sagaId;

    private Long orderId;

    private Instant createdAt;

    private SagaStatus status;
}
