package com.congthanh.cartservice.cqrs.command.aggregate;

import com.congthanh.cartservice.cqrs.command.command.AddItemToCartCommand;
import com.congthanh.cartservice.cqrs.command.event.cartItem.AddItemToCartCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CartItemAggregate {

    @AggregateIdentifier
    private Long id;
    private String productId;
    private String productVariantId;
    private int quantity;
    private Long cartId;
    private Instant createdAt;

    public CartItemAggregate() {}

    @CommandHandler
    public CartItemAggregate(AddItemToCartCommand command) {
        apply(AddItemToCartCreatedEvent.builder()
                .id(command.getId())
                .productId(command.getProductId())
                .productVariantId(command.getProductVariantId())
                .quantity(command.getQuantity())
                .cartId(command.getCartId())
                .createdAt(command.getCreatedAt())
                .build());
    }

    @EventSourcingHandler
    public void on(AddItemToCartCreatedEvent event) {
        this.id = event.getId();
        this.productId = event.getProductId();
        this.productVariantId = event.getProductVariantId();
        this.quantity = event.getQuantity();
        this.cartId = event.getCartId();
        this.createdAt = event.getCreatedAt();
    }
}
