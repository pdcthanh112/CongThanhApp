package com.congthanh.orderservice.cqrs.command.command;

import com.congthanh.orderservice.constant.enums.OrderStatus;

public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    public final String id;

    public final OrderStatus orderStatus;

    public UpdateOrderStatusCommand(String id, String orderStatus) {
        this.id = id;
        this.orderStatus = OrderStatus.valueOf(orderStatus);
    }

}
