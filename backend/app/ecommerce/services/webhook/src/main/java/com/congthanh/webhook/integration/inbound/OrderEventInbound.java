package com.congthanh.webhook.integration.inbound;

import com.congthanh.webhook.service.OrderEventService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventInbound {

    private final OrderEventService orderEventService;

    @KafkaListener(topics = {
            "${webhook.integration.kafka.order.topic-name}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void onOrderEvent(JsonNode productEvent) {
        orderEventService.onOrderEvent(productEvent);
    }
}
