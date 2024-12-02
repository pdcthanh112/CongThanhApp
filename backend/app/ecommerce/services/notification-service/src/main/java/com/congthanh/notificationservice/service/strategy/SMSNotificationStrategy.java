package com.congthanh.notificationservice.service.strategy;

import com.congthanh.notificationservice.model.request.NotificationRequest;
import com.congthanh.notificationservice.service.validator.PhoneNumberValidator;
import com.congthanh.notificationservice.service.validator.SMSValidatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SMSNotificationStrategy implements NotificationStrategy {

    private final TwilioService twilioService;

    @Override
    public boolean send(NotificationRequest request) {
        try {
            twilioService.sendSMS(
                    request.getPhoneNumber(),
                    request.getContent()
            );
            return true;
        } catch (Exception e) {
            log.error("SMS sending failed", e);
            return false;
        }
    }

    @Override
    public boolean validate(NotificationRequest request) {
        return SMSValidatorUtils.phoneNumberValidate(request.getPhoneNumber());
    }
}
