package com.congthanh.customerservice.rabbitmq.shippingAddress;

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
public class ShippingAddressQueueEvent<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ShippingAddressEventType eventType;

    private T data;

}
