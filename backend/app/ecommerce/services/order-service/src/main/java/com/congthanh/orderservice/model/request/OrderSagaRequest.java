package com.congthanh.orderservice.model.request;

import com.congthanh.orderservice.model.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderSagaRequest {

    private Long orderId;

    private String customer;

    private BigDecimal totalAmount;

    private List<OrderItem> items;

    private String deliveryAddress;
}
