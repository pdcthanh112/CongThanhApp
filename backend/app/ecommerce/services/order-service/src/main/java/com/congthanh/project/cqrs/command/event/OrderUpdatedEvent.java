package com.congthanh.project.cqrs.command.event;

import com.congthanh.project.constant.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdatedEvent {

    public final String orderId;

    public final OrderStatus orderStatus;

}
