package com.congthanh.webhook.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface ProductEventService {

    void onProductEvent(JsonNode updatedEvent);
}
