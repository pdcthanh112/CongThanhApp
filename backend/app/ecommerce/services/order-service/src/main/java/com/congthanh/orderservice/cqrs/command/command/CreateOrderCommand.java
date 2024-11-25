package com.congthanh.orderservice.cqrs.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    public final Long id;

    public final String note;

    public final BigDecimal total;

    public final String status;

}
