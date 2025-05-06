package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.entity.OrderItem;
import com.congthanh.orderservice.model.request.OrderSagaRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryRequestEvent extends SagaEvent{

    private Long orderId;

    private OrderSagaRequest request;

    private List<OrderItem> items;
}
