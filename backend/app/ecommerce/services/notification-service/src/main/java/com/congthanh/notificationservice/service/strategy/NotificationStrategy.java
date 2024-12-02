package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.model.request.NotificationRequest;
import jakarta.mail.MessagingException;

public interface NotificationStrategy {

    boolean send(NotificationRequest request) throws MessagingException;

    boolean validate(NotificationRequest request);

}
