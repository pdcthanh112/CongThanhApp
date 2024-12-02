package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.model.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FCMNotificationStrategy implements NotificationStrategy {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public boolean send(NotificationRequest request) {
        try {
            Message message = Message.builder()
                    .setToken(request.getDeviceToken())
                    .setNotification(
                            Notification.builder()
                                    .setTitle(request.getSubject())
                                    .setBody(request.getContent())
                                    .build()
                    )
                    .build();

            String response = firebaseMessaging.send(message);
            return true;
        } catch (Exception e) {
            log.error("FCM notification failed", e);
            return false;
        }
    }

    @Override
    public boolean validate(NotificationRequest request) {
        return request.getDeviceToken() != null;
    }
}
