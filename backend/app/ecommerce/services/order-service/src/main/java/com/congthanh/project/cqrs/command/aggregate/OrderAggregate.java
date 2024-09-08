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
import org.axonframework.modelling.command.AggregateLifecycle;
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

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getId(), command.getNote(), command.getTotal(), command.getStatus()));
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        apply(new OrderCompletedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        apply(new OrderCanceledEvent(command.getOrderId(), command.getReason()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.id = event.getId();
        this.note = event.getNote();
        this.orderLineItems = new ArrayList<>(event.getOrderLineItems());
        this.status = OrderStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.status = OrderStatus.COMPLETED;
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent event) {
        this.status = OrderStatus.CANCELED;
    }

//    @AggregateIdentifier
//    private Long id;
//    private String note;
//    private BigDecimal total;
//    private OrderStatus orderStatus;
//
//    @CommandHandler
//    public OrderAggregate(CreateOrderCommand createOrderCommand){
//        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.id,
//                createOrderCommand.note, createOrderCommand.total, createOrderCommand.orderStatus));
//    }
//
//    @EventSourcingHandler
//    protected void on(OrderCreatedEvent orderCreatedEvent){
//        this.id = orderCreatedEvent.id;
//        this.note = orderCreatedEvent.note;
//        this.total = orderCreatedEvent.total;
//        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
//    }
//
//    @CommandHandler
//    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
//        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.id, updateOrderStatusCommand.orderStatus.name()));
//    }
//
//    @EventSourcingHandler
//    protected void on(OrderUpdatedEvent orderUpdatedEvent){
//        this.id = id;
//        this.orderStatus = OrderStatus.valueOf(String.valueOf(orderUpdatedEvent.orderStatus));
//    }

}
