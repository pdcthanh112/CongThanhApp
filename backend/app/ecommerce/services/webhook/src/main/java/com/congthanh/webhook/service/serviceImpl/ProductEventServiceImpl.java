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
import com.congthanh.webhook.service.ProductEventService;
import com.congthanh.webhook.service.WebhookService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductEventServiceImpl extends AbstractWebhookEventNotificationService implements ProductEventService  {

    private final EventRepository eventRepository;

    private final WebhookService webhookService;

    private final WebhookEventNotificationRepository webhookEventNotificationRepository;

    @Override
    protected WebhookEventNotificationRepository getWebhookEventNotificationRepository() {
        return webhookEventNotificationRepository;
    }

    @Override
    public void onProductEvent(JsonNode updatedEvent) {
        String operation = updatedEvent.get("op").asText();
        if (!Objects.equals(operation, Operation.UPDATE.getName())) {
            return;
        }
        Event event = eventRepository.findByName(EventName.ON_PRODUCT_UPDATED)
                .orElseThrow(() -> new NotFoundException(MessageCode.EVENT_NOT_FOUND, EventName.ON_PRODUCT_UPDATED));
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
