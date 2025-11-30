package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.constant.enums.NotificationChannel;
import com.congthanh.notificationservice.model.request.NotificationRequest;
import com.congthanh.notificationservice.service.validator.SMSValidatorUtils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SMSNotificationStrategy implements NotificationStrategy {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    @Override
    public NotificationChannel getMethod() {
        return NotificationChannel.SMS;
    }

    @Override
    public void send(NotificationRequest request) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber(request.getRecipient()),
                    new PhoneNumber(fromPhoneNumber),
                    request.getContent()
            ).create();

            if (message.getStatus() == Message.Status.QUEUED || message.getStatus() == Message.Status.SENT) {
                request.setStatus("SENT");
            } else {
                request.setStatus("FAILED");
            }
        } catch (Exception e) {
            request.setStatus("FAILED");
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }

    @Override
    public boolean validate(NotificationRequest request) {
        return SMSValidatorUtils.phoneNumberValidate(request.getPhoneNumber());
    }
}
