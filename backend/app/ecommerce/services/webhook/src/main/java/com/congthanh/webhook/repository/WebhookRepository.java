package com.congthanh.webhook.repository;

import com.congthanh.webhook.model.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, Long> {
}
