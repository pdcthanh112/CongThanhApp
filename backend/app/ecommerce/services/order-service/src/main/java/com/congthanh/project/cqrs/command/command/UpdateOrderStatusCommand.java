package com.congthanh.project.cqrs.command.command;

import com.congthanh.project.constant.enums.OrderStatus;

public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    public final String id;

    public final OrderStatus orderStatus;

    public UpdateOrderStatusCommand(String id, String orderStatus) {
        this.id = id;
        this.orderStatus = OrderStatus.valueOf(orderStatus);
    }

}
