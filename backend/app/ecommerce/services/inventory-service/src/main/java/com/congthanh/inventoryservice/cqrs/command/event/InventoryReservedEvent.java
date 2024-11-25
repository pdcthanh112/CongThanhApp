package com.congthanh.inventoryservice.cqrs.command.event;

import lombok.Data;

@Data
public class InventoryReservedEvent {

    private final String id;

    private final String orderId;

    private final double totalAmount;

}
