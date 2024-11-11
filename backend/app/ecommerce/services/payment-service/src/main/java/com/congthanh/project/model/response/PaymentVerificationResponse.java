package com.congthanh.project.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentVerificationResponse {
    private String paymentId;
    private boolean verified;
    private String message;
    private List<String> errors;
}
