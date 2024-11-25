package com.congthanh.paymentservice.model.response;

import com.congthanh.paymentservice.constant.enums.PaymentStatus;

import java.time.Instant;

public class PaymentStatusResponse {
    private String paymentId;
    private PaymentStatus status;
    private String transactionId;
    private Instant processedAt;
    private String message;
}
