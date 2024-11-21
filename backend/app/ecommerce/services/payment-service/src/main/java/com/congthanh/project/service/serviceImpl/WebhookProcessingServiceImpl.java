package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.service.WebhookProcessingService;
import org.springframework.stereotype.Service;

@Service
public class WebhookProcessingServiceImpl implements WebhookProcessingService {

    @Override
    public String processStripeWebhook(String signature, String payload) {
        return "";
    }

    @Override
    public String processPaypalWebhook(String signature, String payload) {
        return "";
    }
}
