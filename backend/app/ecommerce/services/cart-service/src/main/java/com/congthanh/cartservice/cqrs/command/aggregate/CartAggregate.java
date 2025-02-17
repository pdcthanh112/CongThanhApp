package com.congthanh.cartservice.cqrs.command.aggregate;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.congthanh.cartservice.cqrs.command.command.CreateCartCommand;
import com.congthanh.cartservice.cqrs.command.event.cart.CartCreatedEvent;
import com.congthanh.cartservice.model.dto.CartItemDTO;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CartAggregate {

    @AggregateIdentifier
    private Long id;
    private String name;
    private String customer;
    private Instant createdAt;
    private Instant updatedAt;
    private CartStatus status;
    private Set<CartItemDTO> cartItems;
    private boolean isDefault;

    public CartAggregate() {
    }

    @CommandHandler
    public CartAggregate(CreateCartCommand command) {
        apply(CartCreatedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .customerId(command.getCustomerId())
                .createdAt(command.getCreatedAt())
                .updatedAt(command.getUpdatedAt())
                .status(command.getStatus())
                .isDefault(command.isDefault())
                .build());
    }

    @EventSourcingHandler
    public void onCartCreated(CartCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.customer = event.getCustomerId();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
        this.status = event.getStatus();
        this.isDefault = event.isDefault();
    }
}
