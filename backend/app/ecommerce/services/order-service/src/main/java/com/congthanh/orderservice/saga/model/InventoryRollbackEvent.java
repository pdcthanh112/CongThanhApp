package com.congthanh.orderservice.saga.model;

import com.congthanh.orderservice.model.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryRollbackEvent extends SagaEvent {

    private List<OrderItem> items;
}
