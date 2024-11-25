package com.congthanh.cartservice.cqrs.command.aggregate;

import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.Set;

@Aggregate
public class CartAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String customer;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;
    private Set<CartItemDTO> cartItems;
    @JsonProperty("isDefault") private boolean isDefault;

    public CartAggregate() {}

}
