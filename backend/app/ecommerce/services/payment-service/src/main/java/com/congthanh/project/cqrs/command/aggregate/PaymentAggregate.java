package com.congthanh.project.cqrs.command.aggregate;

import com.congthanh.project.cqrs.command.command.ProcessPaymentCommand;
import com.congthanh.project.cqrs.command.event.PaymentFailedEvent;
import com.congthanh.project.cqrs.command.event.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private boolean processed;

    @CommandHandler
    public void handle(ProcessPaymentCommand command) {
        // In a real-world scenario, you would integrate with a payment gateway here
        if (command.getAmount() > 0 && Math.random() > 0.3) { // 70% chance of success for demonstration
            apply(new PaymentProcessedEvent(command.getPaymentId(), command.getOrderId(), command.getAmount()));
        } else {
            apply(new PaymentFailedEvent(command.getPaymentId(), command.getOrderId(), "Payment processing failed"));
        }
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getId();
        this.orderId = event.getOrderId();
        this.amount = event.getAmount();
        this.processed = true;
    }

    @EventSourcingHandler
    public void on(PaymentFailedEvent event) {
        this.paymentId = event.getId();
        this.orderId = event.getOrderId();
        this.processed = false;
    }

}