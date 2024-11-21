package com.congthanh.project.service;

public interface WebhookProcessingService {

    String processStripeWebhook(String signature, String payload);

    String processPaypalWebhook(String signature, String payload);

}
