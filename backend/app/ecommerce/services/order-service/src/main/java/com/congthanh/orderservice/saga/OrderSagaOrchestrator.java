package com.congthanh.orderservice.saga;

import com.congthanh.orderservice.model.entity.OrderSaga;
import com.congthanh.orderservice.model.request.OrderSagaRequest;
import com.congthanh.orderservice.repository.orderSaga.OrderSagaRepository;
import com.congthanh.orderservice.saga.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderSagaOrchestrator {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderSagaRepository sagaRepository;

    private static final String ORDER_CREATED_TOPIC = "order-created";
    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";
    private static final String INVENTORY_UPDATED_TOPIC = "inventory-updated";
    private static final String INVENTORY_FAILED_TOPIC = "inventory-failed";
    private static final String DELIVERY_CREATED_TOPIC = "delivery-created";
    private static final String DELIVERY_FAILED_TOPIC = "delivery-failed";
    private static final String NOTIFICATION_TOPIC = "notification";

    public void startOrderSaga(OrderSagaRequest request) {
        String sagaId = UUID.randomUUID().toString();

        OrderSaga saga = new OrderSaga();
        saga.setSagaId(sagaId);
        saga.setOrderId(request.getOrderId());
        saga.setCurrentState(OrderSagaState.ORDER_CREATED);
        sagaRepository.save(saga);

        // Gửi sự kiện order created
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setSagaId(sagaId);
        event.setRequest(request);

        kafkaTemplate.send(ORDER_CREATED_TOPIC, event);
    }

    @KafkaListener(topics = "payment-processed")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.ORDER_CREATED) {
            // Cập nhật trạng thái saga
            saga.setCurrentState(OrderSagaState.PAYMENT_COMPLETED);
            sagaRepository.save(saga);

            // Gửi yêu cầu cập nhật inventory
            InventoryRequestEvent inventoryEvent = new InventoryRequestEvent();
            inventoryEvent.setSagaId(sagaId);
            inventoryEvent.setOrderId(saga.getOrderId());
            inventoryEvent.setItems(event.getRequest().getItems());

            kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, inventoryEvent);
        }
    }

    @KafkaListener(topics = "payment-failed")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.ORDER_CREATED) {
            // Cập nhật trạng thái
            saga.setCurrentState(OrderSagaState.PAYMENT_FAILED);
            sagaRepository.save(saga);

            // Gửi yêu cầu hủy đơn hàng
            OrderCancelEvent cancelEvent = new OrderCancelEvent();
            cancelEvent.setSagaId(sagaId);
            cancelEvent.setOrderId(saga.getOrderId());
            cancelEvent.setReason("Payment failed: " + event.getReason());

            kafkaTemplate.send(PAYMENT_FAILED_TOPIC, cancelEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("PAYMENT_FAILED");
            notificationEvent.setRecipientId(event.getRequest().getCustomer());
            notificationEvent.setMessage("Payment failed for order " + saga.getOrderId() + ": " + event.getReason());

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
        }
    }

    @KafkaListener(topics = "inventory-updated")
    public void handleInventoryUpdated(InventoryUpdatedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.PAYMENT_COMPLETED) {
            // Cập nhật trạng thái
            saga.setCurrentState(OrderSagaState.INVENTORY_UPDATED);
            sagaRepository.save(saga);

            // Gửi yêu cầu tạo giao hàng
            DeliveryCreateEvent deliveryEvent = new DeliveryCreateEvent();
            deliveryEvent.setSagaId(sagaId);
            deliveryEvent.setOrderId(saga.getOrderId());
            deliveryEvent.setDeliveryAddress(event.getRequest().getDeliveryAddress());

            kafkaTemplate.send(INVENTORY_UPDATED_TOPIC, deliveryEvent);
        }
    }

    @KafkaListener(topics = "inventory-failed")
    public void handleInventoryFailed(InventoryFailedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.PAYMENT_COMPLETED) {
            // Cập nhật trạng thái
            saga.setCurrentState(OrderSagaState.INVENTORY_FAILED);
            sagaRepository.save(saga);

            // Hoàn tiền cho khách hàng
            PaymentRefundEvent refundEvent = new PaymentRefundEvent();
            refundEvent.setSagaId(sagaId);
            refundEvent.setOrderId(saga.getOrderId());
            refundEvent.setAmount(event.getRequest().getTotalAmount());

            kafkaTemplate.send(INVENTORY_FAILED_TOPIC, refundEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("INVENTORY_FAILED");
            notificationEvent.setRecipientId(event.getRequest().getCustomer());
            notificationEvent.setMessage("We're sorry, some items in your order " + saga.getOrderId() + " are out of stock.");

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
        }
    }

    @KafkaListener(topics = "delivery-created")
    public void handleDeliveryCreated(DeliveryCreatedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.INVENTORY_UPDATED) {
            // Cập nhật trạng thái
            saga.setCurrentState(OrderSagaState.DELIVERY_CREATED);
            sagaRepository.save(saga);

            // Cập nhật trạng thái đơn hàng thành công
            OrderCompletedEvent completedEvent = new OrderCompletedEvent();
            completedEvent.setSagaId(sagaId);
            completedEvent.setOrderId(saga.getOrderId());

            kafkaTemplate.send(DELIVERY_CREATED_TOPIC, completedEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("ORDER_COMPLETED");
            notificationEvent.setRecipientId(event.getOrderDTO().getCustomer());
            notificationEvent.setMessage("Your order " + saga.getOrderId() + " has been processed successfully and is out for delivery!");

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
        }
    }

    public void handleDeliveryFailed(DeliveryFailedEvent event) {
        String sagaId = event.getSagaId();
        OrderSaga saga = sagaRepository.findBySagaId(sagaId);

        if (saga != null && saga.getCurrentState() == OrderSagaState.INVENTORY_UPDATED) {
            // Cập nhật trạng thái
            saga.setCurrentState(OrderSagaState.DELIVERY_FAILED);
            sagaRepository.save(saga);

            // Khôi phục inventory
            InventoryRollbackEvent inventoryRollbackEvent = new InventoryRollbackEvent();
            inventoryRollbackEvent.setSagaId(sagaId);
            inventoryRollbackEvent.setOrderId(saga.getOrderId());
            inventoryRollbackEvent.setItems(event.getRequest().getItems());

            kafkaTemplate.send(DELIVERY_FAILED_TOPIC, inventoryRollbackEvent);

            // Hoàn tiền
            PaymentRefundEvent refundEvent = new PaymentRefundEvent();
            refundEvent.setSagaId(sagaId);
            refundEvent.setOrderId(saga.getOrderId());
            refundEvent.setAmount(event.getRequest().getTotalAmount());

            kafkaTemplate.send(DELIVERY_FAILED_TOPIC, refundEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("DELIVERY_FAILED");
            notificationEvent.setRecipientId(event.getRequest().getCustomer());
            notificationEvent.setMessage("We couldn't process the delivery for your order " + saga.getOrderId() + ". Your payment will be refunded.");

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
        }
    }
}
