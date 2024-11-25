package com.congthanh.orderservice.cqrs.command.event;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdatedEvent {

    public final String orderId;

    public final OrderStatus orderStatus;

}
