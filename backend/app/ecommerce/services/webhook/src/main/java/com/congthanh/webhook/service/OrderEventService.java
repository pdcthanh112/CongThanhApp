package com.congthanh.webhook.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface OrderEventService {

    void onOrderEvent(JsonNode updatedEvent);
}
