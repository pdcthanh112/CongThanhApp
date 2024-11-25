package com.congthanh.inventoryservice.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
public class ReleaseInventoryCommand {

    @TargetAggregateIdentifier
    private final String inventoryId;
    private final String orderId;
    private final List<OrderLineItem> orderLineItems;

    @Data
    public static class OrderLineItem {
        private final String productId;
        private final int quantity;
    }

}
