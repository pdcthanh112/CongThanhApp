package com.congthanh.notificationservice.model.request;

public record FCMSubscriptionRequest(String userId, String deviceToken) {
}
