package com.congthanh.project.cqrs.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    public final String id;

    public final String note;

    public final BigDecimal total;

    public final String orderStatus;

    public CreateOrderCommand(String id, String note, BigDecimal total, String orderStatus) {
        this.id = id;
        this.note = note;
        this.total = total;
        this.orderStatus = orderStatus;
    }

}
