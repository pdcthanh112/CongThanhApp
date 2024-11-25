package com.congthanh.orderservice.cqrs.command.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCreatedEvent {

    public final Long id;

    public final String note;

    public final BigDecimal total;

    public final String orderStatus;

}
