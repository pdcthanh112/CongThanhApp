package com.congthanh.orderservice.saga.listener;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.model.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @KafkaListener(topics = "order-completed")
    public void handleOrderCompleted(OrderCompletedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + event.getOrderId()));

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    @KafkaListener(topics = "order-cancelled")
    public void handleOrderCancelled(OrderCancelEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + event.getOrderId()));

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason(event.getReason());
        orderRepository.save(order);
    }
}
