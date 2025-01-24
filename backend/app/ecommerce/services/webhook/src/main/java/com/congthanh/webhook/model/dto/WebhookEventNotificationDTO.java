package com.congthanh.webhook.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookEventNotificationDTO {

    private Long notificationId;

    private String url;

    private String secret;

    private JsonNode payload;

}
