package com.congthanh.orderservice.listener.saga.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public abstract class SagaEvent implements Serializable {

    private String sagaId;
    private Instant createdAt;
    private SagaStatus status;
}
