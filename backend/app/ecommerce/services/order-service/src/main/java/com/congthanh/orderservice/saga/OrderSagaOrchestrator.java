package com.congthanh.orderservice.saga;

import com.congthanh.orderservice.saga.model.OrderCreatedEvent;
import com.congthanh.orderservice.saga.model.OrderSagaState;
import com.congthanh.orderservice.saga.model.PaymentProcessedEvent;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
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

    private static final String ORDER_CREATED_TOPIC = "order-created";
    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";
    private static final String INVENTORY_UPDATED_TOPIC = "inventory-updated";
    private static final String INVENTORY_FAILED_TOPIC = "inventory-failed";
    private static final String DELIVERY_CREATED_TOPIC = "delivery-created";
    private static final String DELIVERY_FAILED_TOPIC = "delivery-failed";
    private static final String NOTIFICATION_TOPIC = "notification";

    // Bắt đầu saga
    public void startOrderSaga(CreateOrderRequest request) {
        String sagaId = UUID.randomUUID().toString();

        // Lưu trạng thái saga trong memory
        sagaStates.put(sagaId, OrderSagaState.ORDER_CREATED);
        sagaOrderIds.put(sagaId, request.getOrderId());
        sagaOrderDTOs.put(sagaId, request);

        // Gửi sự kiện order created
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setSagaId(sagaId);
        event.setRequest(request);

        kafkaTemplate.send(ORDER_CREATED_TOPIC, event);

        log.info("Started order saga {} for order {}", sagaId, request.getOrderId());
    }

    // Xử lý khi payment thành công
    @KafkaListener(topics = "payment-processed")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        String sagaId = event.getSagaId();
        OrderSagaState currentState = sagaStates.get(sagaId);

        if (currentState == OrderSagaState.ORDER_CREATED) {
            // Cập nhật trạng thái saga
            sagaStates.put(sagaId, OrderSagaState.PAYMENT_COMPLETED);

            log.info("Payment processed for saga {}, sending inventory request", sagaId);

            // Gửi yêu cầu cập nhật inventory
            InventoryRequestEvent inventoryEvent = new InventoryRequestEvent();
            inventoryEvent.setSagaId(sagaId);
            inventoryEvent.setOrderId(sagaOrderIds.get(sagaId));
            inventoryEvent.setOrderDTO(sagaOrderDTOs.get(sagaId));
            inventoryEvent.setItems(event.getOrderDTO().getItems());

            kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, inventoryEvent);
        } else {
            log.warn("Received payment processed event for saga {} in invalid state: {}",
                    sagaId, currentState);
        }
    }

    // Xử lý khi payment thất bại
    @KafkaListener(topics = "payment-failed")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        String sagaId = event.getSagaId();
        OrderSagaState currentState = sagaStates.get(sagaId);

        if (currentState == OrderSagaState.ORDER_CREATED) {
            // Cập nhật trạng thái
            sagaStates.put(sagaId, OrderSagaState.PAYMENT_FAILED);

            log.info("Payment failed for saga {}, cancelling order", sagaId);

            // Gửi yêu cầu hủy đơn hàng
            OrderCancelEvent cancelEvent = new OrderCancelEvent();
            cancelEvent.setSagaId(sagaId);
            cancelEvent.setOrderId(sagaOrderIds.get(sagaId));
            cancelEvent.setReason("Payment failed: " + event.getReason());

            kafkaTemplate.send(PAYMENT_FAILED_TOPIC, cancelEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("PAYMENT_FAILED");
            notificationEvent.setRecipientId(event.getOrderDTO().getCustomerId());
            notificationEvent.setMessage("Payment failed for order " + sagaOrderIds.get(sagaId) +
                    ": " + event.getReason());

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);

            // Cleanup cache
            cleanupSaga(sagaId);
        } else {
            log.warn("Received payment failed event for saga {} in invalid state: {}",
                    sagaId, currentState);
        }
    }

    // Xử lý khi inventory cập nhật thành công
    @KafkaListener(topics = "inventory-updated")
    public void handleInventoryUpdated(InventoryUpdatedEvent event) {
        String sagaId = event.getSagaId();
        OrderSagaState currentState = sagaStates.get(sagaId);

        if (currentState == OrderSagaState.PAYMENT_COMPLETED) {
            // Cập nhật trạng thái
            sagaStates.put(sagaId, OrderSagaState.INVENTORY_UPDATED);

            log.info("Inventory updated for saga {}, creating delivery", sagaId);

            // Gửi yêu cầu tạo giao hàng
            DeliveryCreateEvent deliveryEvent = new DeliveryCreateEvent();
            deliveryEvent.setSagaId(sagaId);
            deliveryEvent.setOrderId(sagaOrderIds.get(sagaId));
            deliveryEvent.setOrderDTO(sagaOrderDTOs.get(sagaId));
            deliveryEvent.setDeliveryAddress(event.getOrderDTO().getDeliveryAddress());

            kafkaTemplate.send(INVENTORY_UPDATED_TOPIC, deliveryEvent);
        } else {
            log.warn("Received inventory updated event for saga {} in invalid state: {}",
                    sagaId, currentState);
        }
    }

    // Xử lý khi inventory cập nhật thất bại
    @KafkaListener(topics = "inventory-failed")
    public void handleInventoryFailed(InventoryFailedEvent event) {
        String sagaId = event.getSagaId();
        OrderSagaState currentState = sagaStates.get(sagaId);

        if (currentState == OrderSagaState.PAYMENT_COMPLETED) {
            // Cập nhật trạng thái
            sagaStates.put(sagaId, OrderSagaState.INVENTORY_FAILED);

            log.info("Inventory failed for saga {}, processing refund", sagaId);

            // Hoàn tiền cho khách hàng
            PaymentRefundEvent refundEvent = new PaymentRefundEvent();
            refundEvent.setSagaId(sagaId);
            refundEvent.setOrderId(sagaOrderIds.get(sagaId));
            refundEvent.setAmount(sagaOrderDTOs.get(sagaId).getTotalAmount());

            kafkaTemplate.send(INVENTORY_FAILED_TOPIC, refundEvent);

            // Hủy đơn hàng
            OrderCancelEvent cancelEvent = new OrderCancelEvent();
            cancelEvent.setSagaId(sagaId);
            cancelEvent.setOrderId(sagaOrderIds.get(sagaId));
            cancelEvent.setReason("Inventory unavailable: " + event.getReason());

            kafkaTemplate.send(INVENTORY_FAILED_TOPIC, cancelEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("INVENTORY_FAILED");
            notificationEvent.setRecipientId(event.getOrderDTO().getCustomerId());
            notificationEvent.setMessage("We're sorry, some items in your order " +
                    sagaOrderIds.get(sagaId) + " are out of stock.");

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);

            // Cleanup cache
            cleanupSaga(sagaId);
        } else {
            log.warn("Received inventory failed event for saga {} in invalid state: {}",
                    sagaId, currentState);
        }
    }

    // Xử lý khi tạo giao hàng thành công
    @KafkaListener(topics = "delivery-created")
    public void handleDeliveryCreated(DeliveryCreatedEvent event) {
        String sagaId = event.getSagaId();
        OrderSagaState currentState = sagaStates.get(sagaId);

        if (currentState == OrderSagaState.INVENTORY_UPDATED) {
            // Cập nhật trạng thái
            sagaStates.put(sagaId, OrderSagaState.DELIVERY_CREATED);

            log.info("Delivery created for saga {}, completing order", sagaId);

            // Cập nhật trạng thái đơn hàng thành công
            OrderCompletedEvent completedEvent = new OrderCompletedEvent();
            completedEvent.setSagaId(sagaId);
            completedEvent.setOrderId(sagaOrderIds.get(sagaId));

            kafkaTemplate.send(DELIVERY_CREATED_TOPIC, completedEvent);

            // Gửi thông báo cho khách hàng
            NotificationEvent notificationEvent = new NotificationEvent();
            notificationEvent.setType("ORDER_COMPLETED");
            notificationEvent.setRecipientId(sagaOrderDTOs.get(sagaId).getCustomerId());
            notificationEvent.setMessage("Your order " + sagaOrderIds.get(sagaId) +
                    " has been processed successfully and is out for delivery!");

            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);

            // Saga hoàn thành, dọn dẹp cache
            cleanupSaga(sagaId);
        } else {
            log.warn("Received delivery created event for saga {} in
}
    }
}
