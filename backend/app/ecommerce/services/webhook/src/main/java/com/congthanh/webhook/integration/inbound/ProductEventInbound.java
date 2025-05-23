package com.congthanh.webhook.integration.inbound;

import com.congthanh.webhook.service.ProductEventService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventInbound {

    private final ProductEventService productEventService;

    @KafkaListener(topics = {
            "${webhook.integration.kafka.product.topic-name}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void onProductEvent(JsonNode productEvent) {
        productEventService.onProductEvent(productEvent);
    }
}
