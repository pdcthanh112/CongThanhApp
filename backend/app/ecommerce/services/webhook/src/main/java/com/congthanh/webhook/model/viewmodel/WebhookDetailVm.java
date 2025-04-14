package com.congthanh.webhook.model.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebhookDetailVm {

    private Long id;

    private String payloadUrl;

    private String secret;

    private String contentType;

    private Boolean isActive;

    private List<EventVm> events;
}
