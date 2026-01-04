package com.congthanh.orderservice.saga;

import com.congthanh.inventoryservice.grpc.InventoryItem;
import com.congthanh.inventoryservice.grpc.ReserveInventoryRequest;
import com.congthanh.inventoryservice.grpc.ReserveInventoryResponse;
import com.congthanh.orderservice.constant.common.OrderSagaState;
import com.congthanh.orderservice.grpc.client.InventoryGrpcClient;
import com.congthanh.orderservice.grpc.client.ProductGrpcClient;
import com.congthanh.orderservice.grpc.client.PromotionGrpcClient;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.saga.model.OrderSaga;
import com.congthanh.orderservice.repository.orderSaga.OrderSagaRepository;
import com.congthanh.orderservice.saga.model.*;
import com.congthanh.orderservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.modelling.saga.SagaExecutionException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderSagaOrchestrator {

    private final ProductGrpcClient productServiceClient;

    private final InventoryGrpcClient inventoryServiceClient;

    private final PromotionGrpcClient promotionGrpcClient;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderSagaRepository sagaRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private static final String ORDER_CREATED_TOPIC = "order-created";
    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";
    private static final String INVENTORY_UPDATED_TOPIC = "inventory-updated";
    private static final String INVENTORY_FAILED_TOPIC = "inventory-failed";
    private static final String DELIVERY_CREATED_TOPIC = "delivery-created";
    private static final String DELIVERY_FAILED_TOPIC = "delivery-failed";
    private static final String NOTIFICATION_TOPIC = "notification";

    @Transactional
    public OrderSagaExecution createOrder(CreateOrderRequest request) {
        OrderSaga saga = OrderSaga.builder()
                .sagaId(UUID.randomUUID().toString())
                .orderId(snowflakeIdGenerator.nextId())
                .state(OrderSagaState.STARTED)
                .build();
        sagaRepository.save(saga);

        try {
            executeSaga(saga);
            return OrderSagaExecution.success(saga.getOrderId());
        } catch (Exception e) {
            log.error("Saga execution failed: {}", e.getMessage());
            compensate(saga);
            return OrderSagaExecution.failure(saga.getSagaId(), e.getMessage());
        }
    }

    private void executeSaga(OrderSaga saga) {
        validateProduct(saga);

        validatePromotion(saga);

        reserveInventory(saga);

        processPayment(saga);

        // STEP 4, 5: Notification & Shipping sẽ được trigger sau khi payment success
    }

    private void validateProduct(OrderSaga saga) {
        log.info("[Saga {}] Step 1: Validating products", saga.getSagaId());

        try {
            GetProductsForOrderRequest request = GetProductsForOrderRequest.newBuilder()
                    .addAllProductIds(saga.getRequest().getItems().stream()
                            .map(OrderItem::getProductId)
                            .collect(Collectors.toList()))
                    .build();

            GetProductsForOrderResponse response = productServiceClient.validateProductsForOrder(request);

            if (!response.getIsValid()) {
                throw new SagaExecutionException("Product validation failed: " +
                        response.getInvalidProductsList());
            }

            saga.setState(OrderSagaState.PRODUCT_VALIDATED);
            saga.setProductValidationData(response);
            sagaRepository.save(saga);

            log.info("[Saga {}] Products validated successfully", saga.getSagaId());

        } catch (Exception e) {
            saga.setState(OrderSagaState.FAILED);
            saga.setFailureReason("Product validation failed: " + e.getMessage());
            sagaRepository.save(saga);
            throw new SagaExecutionException("Product validation failed", e);
        }
    }

    private void validatePromotion(OrderSaga saga) {
        log.info("[Saga {}] Step 1: Validating promotions", saga.getSagaId());
    }

    private void reserveInventory(OrderSaga saga) {
        log.info("[Saga {}] Step 2: Reserving inventory", saga.getSagaId());

        try {
            ReserveInventoryRequest request = ReserveInventoryRequest.newBuilder()
                    .setReservationId(saga.getSagaId())
                    .addAllItems(saga.getRequest().getItems().stream()
                            .map(item -> InventoryItem.newBuilder()
                                    .setProductId(item.getProductId())
                                    .setQuantity(item.getQuantity())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            ReserveInventoryResponse response = inventoryServiceClient.reserveInventory(request);

            if (!response.getSuccess()) {
                throw new SagaExecutionException("Inventory reservation failed: " +
                        response.getMessage());
            }

            saga.setState(OrderSagaState.INVENTORY_RESERVED);
            saga.setInventoryReservationId(response.getReservationId());
            sagaRepository.save(saga);

            // Lưu reservation info vào Redis với TTL 15 phút (để rollback nếu cần)
            redisTemplate.opsForValue().set(
                    "inventory:reservation:" + response.getReservationId(),
                    saga.getRequest().getItems(),
                    Duration.ofMinutes(15)
            );

            log.info("[Saga {}] Inventory reserved: {}", saga.getSagaId(),
                    response.getReservationId());

        } catch (Exception e) {
            saga.setState(OrderSagaState.FAILED);
            saga.setFailureReason("Inventory reservation failed: " + e.getMessage());
            sagaRepository.save(saga);
            throw new SagaExecutionException("Inventory reservation failed", e);
        }
    }

    private void processPayment(OrderSaga saga) {
        log.info("[Saga {}] Step 3: Initiating payment processing", saga.getSagaId());

        try {
            saga.setState(OrderSagaState.PAYMENT_PROCESSING);
            sagaRepository.save(saga);

            PaymentRequestEvent event = PaymentRequestEvent.builder()
                    .sagaId(saga.getSagaId())
                    .orderId(saga.getOrderId())
                    .amount(calculateTotalAmount(saga.getRequest()))
                    .customerId(saga.getRequest().getCustomerId())
                    .paymentMethod(saga.getRequest().getPaymentMethod())
                    .timestamp(Instant.now())
                    .build();

            kafkaTemplate.send("payment-request-topic", saga.getOrderId(), event);

            log.info("[Saga {}] Payment request sent to Kafka", saga.getSagaId());

        } catch (Exception e) {
            saga.setState(OrderSagaState.FAILED);
            saga.setFailureReason("Payment initiation failed: " + e.getMessage());
            sagaRepository.save(saga);
            throw new SagaExecutionException("Payment processing failed", e);
        }
    }

    @KafkaListener(topics = "payment-response-topic", groupId = "order-saga-group")
    public void handlePaymentResponse(PaymentResponseEvent event) {
        log.info("[Saga {}] Received payment response: {}", event.getSagaId(),
                event.getStatus());

        OrderSaga saga = sagaRepository.findBySagaId(event.getSagaId())
                .orElseThrow(() -> new SagaNotFoundException("Saga not found: " + event.getSagaId()));

        if (event.getStatus() == PaymentStatus.SUCCESS) {
            saga.setState(OrderSagaState.PAYMENT_COMPLETED);
            saga.setPaymentId(event.getPaymentId());
            sagaRepository.save(saga);

            // Trigger shipping và notification
            createShipping(saga);
            sendNotification(saga);

            // Mark saga as completed
            saga.setState(OrderSagaState.COMPLETED);
            sagaRepository.save(saga);

            log.info("[Saga {}] Order completed successfully", saga.getSagaId());

        } else {
            log.error("[Saga {}] Payment failed: {}", saga.getSagaId(), event.getErrorMessage());
            saga.setState(OrderSagaState.FAILED);
            saga.setFailureReason("Payment failed: " + event.getErrorMessage());
            sagaRepository.save(saga);

            // Trigger compensation
            compensate(saga);
        }
    }

    private void createShipping(OrderSaga saga) {
        log.info("[Saga {}] Step 4: Creating shipping", saga.getSagaId());

        ShippingRequestEvent event = ShippingRequestEvent.builder()
                .sagaId(saga.getSagaId())
                .orderId(saga.getOrderId())
                .customerId(saga.getRequest().getCustomerId())
                .shippingAddress(saga.getRequest().getShippingAddress())
                .items(saga.getRequest().getItems())
                .timestamp(Instant.now())
                .build();

        kafkaTemplate.send("shipping-request-topic", saga.getOrderId(), event);

        log.info("[Saga {}] Shipping request sent", saga.getSagaId());
    }

    private void sendNotification(OrderSaga saga) {
        log.info("[Saga {}] Step 5: Sending notification", saga.getSagaId());

        NotificationEvent event = NotificationEvent.builder()
                .sagaId(saga.getSagaId())
                .orderId(saga.getOrderId())
                .customerId(saga.getRequest().getCustomerId())
                .type(NotificationType.ORDER_CONFIRMED)
                .message("Your order has been confirmed and will be shipped soon")
                .timestamp(Instant.now())
                .build();

        kafkaTemplate.send("notification-topic", saga.getOrderId(), event);

        log.info("[Saga {}] Notification sent", saga.getSagaId());
    }

    private void compensate(OrderSaga saga) {
        log.warn("[Saga {}] Starting compensation", saga.getSagaId());

        saga.setState(OrderSagaState.COMPENSATING);
        sagaRepository.save(saga);

        try {
            // Rollback theo thứ tự ngược lại

            // 1. Cancel Payment (nếu đã payment)
            if (saga.getPaymentId() != null) {
                cancelPayment(saga);
            }

            // 2. Release Inventory (nếu đã reserve)
            if (saga.getInventoryReservationId() != null) {
                releaseInventory(saga);
            }

            saga.setState(OrderSagaState.COMPENSATED);
            sagaRepository.save(saga);

            log.info("[Saga {}] Compensation completed", saga.getSagaId());

            // Gửi notification về việc order bị cancel
            sendCancellationNotification(saga);

        } catch (Exception e) {
            log.error("[Saga {}] Compensation failed: {}", saga.getSagaId(), e.getMessage());
            // Có thể cần manual intervention hoặc retry mechanism
            saga.setCompensationError(e.getMessage());
            sagaRepository.save(saga);
        }
    }

    private void releaseInventory(OrderSaga saga) {
        log.info("[Saga {}] Compensating: Releasing inventory", saga.getSagaId());

        try {
            ReleaseInventoryRequest grpcRequest = ReleaseInventoryRequest.newBuilder()
                    .setReservationId(saga.getInventoryReservationId())
                    .build();

            ReleaseInventoryResponse response = inventoryServiceClient.releaseInventory(grpcRequest);

            if (!response.getSuccess()) {
                log.error("[Saga {}] Failed to release inventory: {}",
                        saga.getSagaId(), response.getMessage());
            }

            // Xóa reservation khỏi Redis
            redisTemplate.delete("inventory:reservation:" + saga.getInventoryReservationId());

            log.info("[Saga {}] Inventory released", saga.getSagaId());

        } catch (Exception e) {
            log.error("[Saga {}] Error releasing inventory: {}", saga.getSagaId(), e.getMessage());
            throw new CompensationException("Failed to release inventory", e);
        }
    }

    private void cancelPayment(OrderSaga saga) {
        log.info("[Saga {}] Compensating: Cancelling payment", saga.getSagaId());

        PaymentCancelEvent event = PaymentCancelEvent.builder()
                .sagaId(saga.getSagaId())
                .orderId(saga.getOrderId())
                .paymentId(saga.getPaymentId())
                .reason("Order saga compensation")
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send("payment-cancel-topic", saga.getOrderId(), event);

        log.info("[Saga {}] Payment cancellation request sent", saga.getSagaId());
    }

    private void sendCancellationNotification(OrderSaga saga) {
        NotificationEvent event = NotificationEvent.builder()
                .sagaId(saga.getSagaId())
                .orderId(saga.getOrderId())
                .customerId(saga.getRequest().getCustomerId())
                .type(NotificationType.ORDER_CANCELLED)
                .message("Your order has been cancelled. Reason: " + saga.getFailureReason())
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send("notification-topic", saga.getOrderId(), event);
    }

    private BigDecimal calculateTotalAmount(CreateOrderRequest request) {
        return request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    @KafkaListener(topics = "payment-processed")
//    public void handlePaymentProcessed(PaymentProcessedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.ORDER_CREATED) {
//            // Cập nhật trạng thái saga
//            saga.setCurrentState(OrderSagaState.PAYMENT_COMPLETED);
//            sagaRepository.save(saga);
//
//            // Gửi yêu cầu cập nhật inventory
//            InventoryRequestEvent inventoryEvent = new InventoryRequestEvent();
//            inventoryEvent.setSagaId(sagaId);
//            inventoryEvent.setOrderId(saga.getOrderId());
//            inventoryEvent.setItems(event.getRequest().getItems());
//
//            kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, inventoryEvent);
//        }
//    }
//
//    @KafkaListener(topics = "payment-failed")
//    public void handlePaymentFailed(PaymentFailedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.ORDER_CREATED) {
//            // Cập nhật trạng thái
//            saga.setCurrentState(OrderSagaState.PAYMENT_FAILED);
//            sagaRepository.save(saga);
//
//            // Gửi yêu cầu hủy đơn hàng
//            OrderCancelEvent cancelEvent = new OrderCancelEvent();
//            cancelEvent.setSagaId(sagaId);
//            cancelEvent.setOrderId(saga.getOrderId());
//            cancelEvent.setReason("Payment failed: " + event.getReason());
//
//            kafkaTemplate.send(PAYMENT_FAILED_TOPIC, cancelEvent);
//
//            // Gửi thông báo cho khách hàng
//            NotificationEvent notificationEvent = new NotificationEvent();
//            notificationEvent.setType("PAYMENT_FAILED");
//            notificationEvent.setRecipientId(event.getRequest().getCustomer());
//            notificationEvent.setMessage("Payment failed for order " + saga.getOrderId() + ": " + event.getReason());
//
//            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
//        }
//    }
//
//    @KafkaListener(topics = "inventory-updated")
//    public void handleInventoryUpdated(InventoryUpdatedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.PAYMENT_COMPLETED) {
//            // Cập nhật trạng thái
//            saga.setCurrentState(OrderSagaState.INVENTORY_UPDATED);
//            sagaRepository.save(saga);
//
//            // Gửi yêu cầu tạo giao hàng
//            DeliveryCreateEvent deliveryEvent = new DeliveryCreateEvent();
//            deliveryEvent.setSagaId(sagaId);
//            deliveryEvent.setOrderId(saga.getOrderId());
//            deliveryEvent.setDeliveryAddress(event.getRequest().getDeliveryAddress());
//
//            kafkaTemplate.send(INVENTORY_UPDATED_TOPIC, deliveryEvent);
//        }
//    }
//
//    @KafkaListener(topics = "inventory-failed")
//    public void handleInventoryFailed(InventoryFailedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.PAYMENT_COMPLETED) {
//            // Cập nhật trạng thái
//            saga.setCurrentState(OrderSagaState.INVENTORY_FAILED);
//            sagaRepository.save(saga);
//
//            // Hoàn tiền cho khách hàng
//            PaymentRefundEvent refundEvent = new PaymentRefundEvent();
//            refundEvent.setSagaId(sagaId);
//            refundEvent.setOrderId(saga.getOrderId());
//            refundEvent.setAmount(event.getRequest().getTotalAmount());
//
//            kafkaTemplate.send(INVENTORY_FAILED_TOPIC, refundEvent);
//
//            // Gửi thông báo cho khách hàng
//            NotificationEvent notificationEvent = new NotificationEvent();
//            notificationEvent.setType("INVENTORY_FAILED");
//            notificationEvent.setRecipientId(event.getRequest().getCustomer());
//            notificationEvent.setMessage("We're sorry, some items in your order " + saga.getOrderId() + " are out of stock.");
//
//            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
//        }
//    }
//
//    @KafkaListener(topics = "delivery-created")
//    public void handleDeliveryCreated(DeliveryCreatedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.INVENTORY_UPDATED) {
//            // Cập nhật trạng thái
//            saga.setCurrentState(OrderSagaState.DELIVERY_CREATED);
//            sagaRepository.save(saga);
//
//            // Cập nhật trạng thái đơn hàng thành công
//            OrderCompletedEvent completedEvent = new OrderCompletedEvent();
//            completedEvent.setSagaId(sagaId);
//            completedEvent.setOrderId(saga.getOrderId());
//
//            kafkaTemplate.send(DELIVERY_CREATED_TOPIC, completedEvent);
//
//            // Gửi thông báo cho khách hàng
//            NotificationEvent notificationEvent = new NotificationEvent();
//            notificationEvent.setType("ORDER_COMPLETED");
//            notificationEvent.setRecipientId(event.getOrderDTO().getCustomer());
//            notificationEvent.setMessage("Your order " + saga.getOrderId() + " has been processed successfully and is out for delivery!");
//
//            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
//        }
//    }
//
//    public void handleDeliveryFailed(DeliveryFailedEvent event) {
//        String sagaId = event.getSagaId();
//        OrderSaga saga = sagaRepository.findBySagaId(sagaId);
//
//        if (saga != null && saga.getCurrentState() == OrderSagaState.INVENTORY_UPDATED) {
//            // Cập nhật trạng thái
//            saga.setCurrentState(OrderSagaState.DELIVERY_FAILED);
//            sagaRepository.save(saga);
//
//            // Khôi phục inventory
//            InventoryRollbackEvent inventoryRollbackEvent = new InventoryRollbackEvent();
//            inventoryRollbackEvent.setSagaId(sagaId);
//            inventoryRollbackEvent.setOrderId(saga.getOrderId());
//            inventoryRollbackEvent.setItems(event.getRequest().getItems());
//
//            kafkaTemplate.send(DELIVERY_FAILED_TOPIC, inventoryRollbackEvent);
//
//            // Hoàn tiền
//            PaymentRefundEvent refundEvent = new PaymentRefundEvent();
//            refundEvent.setSagaId(sagaId);
//            refundEvent.setOrderId(saga.getOrderId());
//            refundEvent.setAmount(event.getRequest().getTotalAmount());
//
//            kafkaTemplate.send(DELIVERY_FAILED_TOPIC, refundEvent);
//
//            // Gửi thông báo cho khách hàng
//            NotificationEvent notificationEvent = new NotificationEvent();
//            notificationEvent.setType("DELIVERY_FAILED");
//            notificationEvent.setRecipientId(event.getRequest().getCustomer());
//            notificationEvent.setMessage("We couldn't process the delivery for your order " + saga.getOrderId() + ". Your payment will be refunded.");
//
//            kafkaTemplate.send(NOTIFICATION_TOPIC, notificationEvent);
//        }
//    }
}
