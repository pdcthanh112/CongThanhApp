package com.congthanh.webhook.service;

import com.congthanh.webhook.model.dto.WebhookEventNotificationDTO;
import com.congthanh.webhook.model.viewmodel.WebhookDetailVm;
import com.congthanh.webhook.model.viewmodel.WebhookListGetVm;
import com.congthanh.webhook.model.viewmodel.WebhookPostVm;
import com.congthanh.webhook.model.viewmodel.WebhookVm;

import java.util.List;

public interface WebhookService {

    WebhookListGetVm getPageableWebhooks(int pageNo, int pageSize);

    List<WebhookVm> findAllWebhooks();

    WebhookDetailVm findById(Long id);

    WebhookDetailVm create(WebhookPostVm webhookPostVm);

    void update(WebhookPostVm webhookPostVm, Long id);

    void delete(Long id);

    void notifyToWebhook(WebhookEventNotificationDTO notificationDto);
}
