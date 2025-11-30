package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.constant.enums.NotificationChannel;
import com.congthanh.notificationservice.model.request.NotificationRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class InAppNotificationStrategy implements NotificationStrategy {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void registerEmitter(String userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    @Override
    public NotificationChannel getMethod() {
        return NotificationChannel.IN_APP;
    }

    @Override
    public void send(NotificationRequest request) throws MessagingException {
        try {
            SseEmitter emitter = emitters.get(request.getRecipient());
            if (emitter != null) {
                emitter.send(SseEmitter.event().name("notification").data(request.getContent()));
                request.setStatus("SENT");
            } else {
                request.setStatus("FAILED");
                throw new RuntimeException("No emitter found for user: " + request.getRecipient());
            }
        } catch (IOException e) {
            request.setStatus("FAILED");
            throw new RuntimeException("Failed to send SSE: " + e.getMessage());
        }
    }

    @Override
    public boolean validate(NotificationRequest request) {
        return true;
    }
}
