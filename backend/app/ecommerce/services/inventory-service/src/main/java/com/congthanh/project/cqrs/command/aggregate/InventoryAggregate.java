package com.congthanh.project.cqrs.command.aggregate;

import com.congthanh.project.cqrs.command.command.ReleaseInventoryCommand;
import com.congthanh.project.cqrs.command.command.ReserveInventoryCommand;
import com.congthanh.project.cqrs.command.event.InventoryReservationFailedEvent;
import com.congthanh.project.cqrs.command.event.InventoryReservedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class InventoryAggregate {

    @AggregateIdentifier
    private String inventoryId;
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryAggregate() {}

    @CommandHandler
    public void handle(ReserveInventoryCommand command) {
        String orderId = command.getOrderId();
        boolean allProductsAvailable = command.getOrderLineItems().stream()
                .allMatch(item -> inventory.getOrDefault(item.getProductId(), 0) >= item.getQuantity());

        if (allProductsAvailable) {
            double totalAmount = command.getOrderLineItems().stream()
                    .mapToDouble(item -> item.getQuantity() * 10.0) // Assuming a fixed price of 10 for simplicity
                    .sum();
            apply(new InventoryReservedEvent(inventoryId, orderId, totalAmount));
        } else {
            apply(new InventoryReservationFailedEvent(inventoryId, orderId, "Insufficient inventory"));
        }
    }

    @CommandHandler
    public void handle(ReleaseInventoryCommand command) {
        // Implementation for releasing inventory
    }

    @EventSourcingHandler
    public void on(InventoryReservedEvent event) {
        this.inventoryId = event.getId();
        // Update inventory levels
    }

}
