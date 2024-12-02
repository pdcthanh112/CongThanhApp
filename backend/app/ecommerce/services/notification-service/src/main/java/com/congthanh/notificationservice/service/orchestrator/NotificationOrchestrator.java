package com.congthanh.notificationservice.service.orchestrator;

import com.congthanh.notificationservice.constant.enums.NotificationType;
import com.congthanh.notificationservice.service.strategy.NotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationOrchestrator {

    private final Map<NotificationType, NotificationStrategy> strategy;

    public NotificationOrchestrator(
            EmailNotificationStrategy emailStrategy,
            SMSNotificationStrategy smsStrategy,
            FCMNotificationStrategy fcmStrategy
    ) {
        strategy = Map.of(
                NotificationType.EMAIL, emailStrategy,
                NotificationType.SMS, smsStrategy,
                NotificationType.FCM, fcmStrategy
        );
    }

    public boolean sendNotification(NotificationRequest request) {
        NotificationStrategy strategy = strategies.get(request.getType());
        if (strategy == null) {
            throw new NotificationException("Unsupported notification type");
        }

        if (!strategy.validate(request)) {
            throw new NotificationException("Invalid notification request");
        }

        return strategy.send(request);
    }

    public void logNotification(NotificationRequest request, boolean status) {
        // Log notification to database
    }
}
