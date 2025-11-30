package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.constant.enums.NotificationChannel;
import com.congthanh.notificationservice.model.request.NotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebPushNotificationStrategy implements NotificationStrategy {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public NotificationChannel getMethod() {
        return NotificationChannel.WEB_PUSH;
    }

    @Override
    public void send(NotificationRequest request) {
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
            if (response != null) {
                request.setStatus("SENT");
            } else {
                request.setStatus("FAILED");
            }
        } catch (Exception e) {
            request.setStatus("FAILED");
            throw new RuntimeException("Failed to send WEB_PUSH notification: " + e.getMessage());
        }
    }

    @Override
    public boolean validate(NotificationRequest request) {
        return request.getDeviceToken() != null;
    }
}
