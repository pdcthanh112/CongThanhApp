package com.congthanh.webhook.service;

import com.congthanh.webhook.model.dto.WebhookEventNotificationDTO;
import com.congthanh.webhook.model.entity.WebhookEvent;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractWebhookEventNotificationService {

    protected abstract WebhookEventNotificationRepository getWebhookEventNotificationRepository();

    protected Long persistNotification(Long webhookEventId, JsonNode payload) {
        WebhookEventNotification notification = new WebhookEventNotification();
        notification.setWebhookEventId(webhookEventId);
        notification.setPayload(payload.toString());
        notification.setNotificationStatus(NotificationStatus.NOTIFYING);
        notification.setCreatedOn(ZonedDateTime.now());
        WebhookEventNotification persistedNotification = getWebhookEventNotificationRepository().save(notification);
        return persistedNotification.getId();
    }

    protected WebhookEventNotificationDTO createNotificationDto(WebhookEvent webhookEvent, JsonNode payload, Long notificationId) {
        String url = webhookEvent.getWebhook().getPayloadUrl();
        String secret = webhookEvent.getWebhook().getSecret();

        return WebhookEventNotificationDTO.builder()
                .secret(secret)
                .payload(payload)
                .url(url)
                .notificationId(notificationId)
                .build();
    }
}
