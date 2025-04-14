package com.congthanh.webhook.model.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebhookListGetVm {

    private List<WebhookVm> webhooks;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private long totalPages;

    private boolean isLast;
}
