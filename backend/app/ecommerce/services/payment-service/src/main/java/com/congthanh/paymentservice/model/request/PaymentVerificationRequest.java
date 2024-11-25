package com.congthanh.paymentservice.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PaymentVerificationRequest {
    @NotNull
    private String paymentId;

    @NotNull
    private String verificationCode;

    private Map<String, String> additionalData;
}
