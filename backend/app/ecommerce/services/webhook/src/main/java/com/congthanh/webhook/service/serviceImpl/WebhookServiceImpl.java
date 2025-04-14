package com.congthanh.webhook.service.serviceImpl;

import com.congthanh.webhook.constain.common.MessageCode;
import com.congthanh.webhook.constain.enums.NotificationStatus;
import com.congthanh.webhook.exception.NotFoundException;
import com.congthanh.webhook.integration.api.WebhookApi;
import com.congthanh.webhook.model.dto.WebhookEventNotificationDTO;
import com.congthanh.webhook.model.entity.Webhook;
import com.congthanh.webhook.model.entity.WebhookEvent;
import com.congthanh.webhook.model.entity.WebhookEventNotification;
import com.congthanh.webhook.model.mapper.WebhookMapper;
import com.congthanh.webhook.model.viewmodel.*;
import com.congthanh.webhook.repository.EventRepository;
import com.congthanh.webhook.repository.WebhookEventNotificationRepository;
import com.congthanh.webhook.repository.WebhookEventRepository;
import com.congthanh.webhook.repository.WebhookRepository;
import com.congthanh.webhook.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final WebhookRepository webhookRepository;

    private final EventRepository eventRepository;

    private final WebhookEventRepository webhookEventRepository;

    private final WebhookEventNotificationRepository webhookEventNotificationRepository;

    private final WebhookMapper webhookMapper;

    private final WebhookApi webHookApi;

    @Override
    public WebhookListGetVm getPageableWebhooks(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Webhook> webhooks = webhookRepository.findAll(pageRequest);
        return webhookMapper.toWebhookListGetVm(webhooks, pageNo, pageSize);
    }

    @Override
    public List<WebhookVm> findAllWebhooks() {
        List<Webhook> webhooks = webhookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return webhooks.stream().map(webhookMapper::toWebhookVm).toList();
    }

    @Override
    public WebhookDetailVm findById(Long id) {
        return webhookMapper.toWebhookDetailVm(webhookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageCode.WEBHOOK_NOT_FOUND, id)));
    }

    @Override
    public WebhookDetailVm create(WebhookPostVm webhookPostVm) {
        Webhook createdWebhook = webhookMapper.toCreatedWebhook(webhookPostVm);
        createdWebhook = webhookRepository.save(createdWebhook);
        if (!CollectionUtils.isEmpty(webhookPostVm.getEvents())) {
            List<WebhookEvent> webhookEvents
                    = initializeWebhookEvents(createdWebhook.getId(), webhookPostVm.getEvents());
            webhookEvents = webhookEventRepository.saveAll(webhookEvents);
            createdWebhook.setWebhookEvents(webhookEvents);
        }
        return webhookMapper.toWebhookDetailVm(createdWebhook);
    }

    @Override
    public void update(WebhookPostVm webhookPostVm, Long id) {
        Webhook existedWebHook = webhookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageCode.WEBHOOK_NOT_FOUND, id));
        Webhook updatedWebhook = webhookMapper.toUpdatedWebhook(existedWebHook, webhookPostVm);
        webhookRepository.save(updatedWebhook);
        webhookEventRepository.deleteAll(existedWebHook.getWebhookEvents().stream().toList());
        if (!CollectionUtils.isEmpty(webhookPostVm.getEvents())) {
            List<WebhookEvent> webhookEvents = initializeWebhookEvents(id, webhookPostVm.getEvents());
            webhookEventRepository.saveAll(webhookEvents);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!webhookRepository.existsById(id)) {
            throw new NotFoundException(MessageCode.WEBHOOK_NOT_FOUND, id);
        }
        webhookEventRepository.deleteByWebhookId(id);
        webhookRepository.deleteById(id);
    }

    @Override
    @Async
    public void notifyToWebhook(WebhookEventNotificationDTO notificationDto) {
        webHookApi.notify(notificationDto.getUrl(), notificationDto.getSecret(), notificationDto.getPayload());
        WebhookEventNotification notification = webhookEventNotificationRepository.findById(
                        notificationDto.getNotificationId())
                .orElseThrow();
        notification.setNotificationStatus(NotificationStatus.NOTIFIED);
        webhookEventNotificationRepository.save(notification);
    }

    private List<WebhookEvent> initializeWebhookEvents(Long webhookId, List<EventVm> events) {
        return events.stream().map(hookEventVm -> {
            WebhookEvent webhookEvent = new WebhookEvent();
            webhookEvent.setWebhookId(webhookId);
            long eventId = hookEventVm.getId();
            eventRepository.findById(eventId)
                    .orElseThrow(() -> new NotFoundException(MessageCode.EVENT_NOT_FOUND, eventId));
            webhookEvent.setEventId(eventId);
            return webhookEvent;
        }).toList();
    }
}
