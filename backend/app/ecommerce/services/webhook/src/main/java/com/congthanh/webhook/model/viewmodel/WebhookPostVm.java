package com.congthanh.webhook.model.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebhookPostVm {

    private String payloadUrl;

    private String secret;

    private String contentType;

    private boolean isActive;

    private List<EventVm> events;
}
