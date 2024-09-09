package com.congthanh.project.cqrs.command.event;

import lombok.Data;

@Data
public class OrderShippedEvent {

    public final String shippingId;

    public final String orderId;

    public final String paymentId;

}
