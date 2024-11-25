package com.congthanh.inventoryservice.cqrs.command.event;

import lombok.Data;

@Data
public class InventoryReservationFailedEvent {

    private final String id;

    private final String orderId;

    private final String reason;

}
