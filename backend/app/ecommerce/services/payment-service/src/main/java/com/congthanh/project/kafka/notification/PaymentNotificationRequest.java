package com.congthanh.project.kafka.notification;

import com.congthanh.project.constant.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(String orderReference,
                                         BigDecimal amount,
                                         PaymentMethod paymentMethod,
                                         String customerFirstname,
                                         String customerLastname,
                                         String customerEmail) {
}
