package com.congthanh.cartservice.rabbitmq.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartQueueEvent<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private CartEventType eventType;

    private T data;
}
