package com.congthanh.webhook.service.serviceImpl;

import com.congthanh.webhook.constain.common.MessageCode;
import com.congthanh.webhook.constain.enums.EventName;
import com.congthanh.webhook.constain.enums.Operation;
import com.congthanh.webhook.exception.NotFoundException;
import com.congthanh.webhook.model.dto.WebhookEventNotificationDTO;
import com.congthanh.webhook.model.entity.Event;
import com.congthanh.webhook.model.entity.WebhookEvent;
import com.congthanh.webhook.repository.EventRepository;
import com.congthanh.webhook.repository.WebhookEventNotificationRepository;
import com.congthanh.webhook.service.AbstractWebhookEventNotificationService;
import com.congthanh.webhook.service.OrderEventService;
import com.congthanh.webhook.service.WebhookService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderEventServiceImpl extends AbstractWebhookEventNotificationService implements OrderEventService {

    private final EventRepository eventRepository;

    private final WebhookService webhookService;

    private final WebhookEventNotificationRepository webhookEventNotificationRepository;

    @Override
    protected WebhookEventNotificationRepository getWebhookEventNotificationRepository() {
        return webhookEventNotificationRepository;
    }

    @Override
    public void onOrderEvent(JsonNode updatedEvent) {
        Optional<EventName> optionalEventName = getEventName(updatedEvent);
        if (optionalEventName.isPresent()) {
            Event event = eventRepository.findByName(optionalEventName.get())
                    .orElseThrow(() -> new NotFoundException(MessageCode.EVENT_NOT_FOUND, optionalEventName.get()));
            List<WebhookEvent> hookEvents = event.getWebhookEvents();
            hookEvents.forEach(hookEvent -> {
                JsonNode payload = updatedEvent.get("after");
                Long notificationId = super.persistNotification(hookEvent.getId(), payload);

                WebhookEventNotificationDTO dto = super.createNotificationDto(hookEvent, payload,
                        notificationId);
                webhookService.notifyToWebhook(dto);
            });
        }
    }

    private Optional<EventName> getEventName(JsonNode updatedEvent) {
        String operation = updatedEvent.get("op").asText();
        if (Objects.equals(operation, Operation.CREATE.getName())) {
            return Optional.of(EventName.ON_ORDER_CREATED);
        }
        if (Objects.equals(operation, Operation.UPDATE.getName())) {
            String orderStatusBefore = updatedEvent.path("before").get("order_status").asText();
            String orderStatusAfter = updatedEvent.path("after").get("order_status").asText();
            if (!orderStatusBefore.equals(orderStatusAfter)) {
                return Optional.of(EventName.ON_ORDER_STATUS_UPDATED);
            }
        }
        return Optional.empty();
    }
}
