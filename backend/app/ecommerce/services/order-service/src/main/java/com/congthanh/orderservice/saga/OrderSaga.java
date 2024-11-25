package com.congthanh.orderservice.saga;

import com.congthanh.orderservice.cqrs.command.event.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Saga started for order: " + orderId);

        // Gửi command để kiểm tra và đặt trước hàng tồn kho
        ReserveInventoryCommand reserveInventoryCommand = new ReserveInventoryCommand(orderId, event.getOrderLineItems());
        commandGateway.send(reserveInventoryCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InventoryReservedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Inventory reserved for order: " + orderId);

        // Gửi command để xử lý thanh toán
        ProcessPaymentCommand processPaymentCommand = new ProcessPaymentCommand(orderId, event.getTotalAmount());
        commandGateway.send(processPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Payment processed for order: " + orderId);

        // Gửi command để hoàn tất đơn hàng
        CompleteOrderCommand completeOrderCommand = new CompleteOrderCommand(orderId);
        commandGateway.send(completeOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Order completed: " + orderId);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCancelledEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Order cancelled: " + orderId);

        // Gửi command để hoàn trả hàng tồn kho (nếu cần)
        ReleaseInventoryCommand releaseInventoryCommand = new ReleaseInventoryCommand(orderId, event.getOrderLineItems());
        commandGateway.send(releaseInventoryCommand);
    }

    // Xử lý các trường hợp thất bại
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InventoryReservationFailedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Inventory reservation failed for order: " + orderId);

        // Gửi command để hủy đơn hàng
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId, "Insufficient inventory");
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentFailedEvent event) {
        String orderId = event.getOrderId();
        System.out.println("Payment failed for order: " + orderId);

        // Gửi command để hủy đơn hàng và hoàn trả hàng tồn kho
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId, "Payment failed");
        commandGateway.send(cancelOrderCommand);

        ReleaseInventoryCommand releaseInventoryCommand = new ReleaseInventoryCommand(orderId, event.getOrderLineItems());
        commandGateway.send(releaseInventoryCommand);
    }
}
