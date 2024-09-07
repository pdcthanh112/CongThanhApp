package com.congthanh.project.cqrs.command.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    public final String id;

    public final String note;

    public final BigDecimal total;

    public final String orderStatus;

    public OrderCreatedEvent(String id, String note, BigDecimal total, String orderStatus) {
        this.id = id;
        this.note = note;
        this.total = total;
        this.orderStatus = orderStatus;
    }
}
