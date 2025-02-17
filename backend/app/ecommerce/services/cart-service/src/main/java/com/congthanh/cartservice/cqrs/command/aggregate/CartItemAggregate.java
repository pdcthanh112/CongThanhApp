package com.congthanh.cartservice.cqrs.command.aggregate;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class CartItemAggregate {

    @AggregateIdentifier
    private Long id;

    public CartItemAggregate() {}
}
