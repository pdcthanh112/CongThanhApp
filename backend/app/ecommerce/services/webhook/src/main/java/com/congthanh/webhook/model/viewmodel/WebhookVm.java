package com.congthanh.webhook.model.viewmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebhookVm {

    private Long id;

    private String payloadUrl;

    private String contentType;

    private Boolean isActive;
}
