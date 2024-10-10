package com.congthanh.project.cqrs.command.aggregate;

import com.congthanh.project.constant.enums.OrderStatus;
import com.congthanh.project.cqrs.command.command.CancelOrderCommand;
import com.congthanh.project.cqrs.command.command.CompleteOrderCommand;
import com.congthanh.project.cqrs.command.command.CreateOrderCommand;
import com.congthanh.project.cqrs.command.command.UpdateOrderStatusCommand;
import com.congthanh.project.cqrs.command.event.OrderCanceledEvent;
import com.congthanh.project.cqrs.command.event.OrderCompletedEvent;
import com.congthanh.project.cqrs.command.event.OrderUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import com.congthanh.project.cqrs.command.event.OrderCreatedEvent;

import java.math.BigDecimal;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private Long id;
    private String note;
    private List<OrderCreatedEvent> orderLineItems;
    private BigDecimal total;
    private OrderStatus status;

    public OrderAggregate() {
    }

    @CommandHandler
    public void handleCreateOrder(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getId(), command.getNote(), command.getTotal(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onOrderCreated(OrderCreatedEvent event) {
        this.id = event.getId();
        this.note = event.getNote();
        this.orderLineItems = new ArrayList<>(event.getOrderLineItems());
        this.status = OrderStatus.CREATED;
    }

    @CommandHandler
    protected void handleUpdateOrder(UpdateOrderStatusCommand updateOrderStatusCommand) {
        apply(new OrderUpdatedEvent(updateOrderStatusCommand.id, updateOrderStatusCommand.orderStatus.name()));
    }

    @EventSourcingHandler
    protected void onOrderUpdated(OrderUpdatedEvent orderUpdatedEvent) {
        this.id = id;
        this.status = OrderStatus.valueOf(String.valueOf(orderUpdatedEvent.orderStatus));
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        apply(new OrderCompletedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.status = OrderStatus.COMPLETED;
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        apply(new OrderCanceledEvent(command.getOrderId(), command.getReason()));
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent event) {
        this.status = OrderStatus.CANCELED;
    }

}
