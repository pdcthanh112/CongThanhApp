package com.congthanh.webhook.repository;

import com.congthanh.webhook.model.entity.WebhookEventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookEventNotificationRepository extends JpaRepository<WebhookEventNotification, Long> {

}
