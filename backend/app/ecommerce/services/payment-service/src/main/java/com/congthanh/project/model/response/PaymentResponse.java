package com.congthanh.project.model.response;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.constant.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String currency;
    private String orderId;

    private String transactionId;
    private String paymentUrl;       // URL để redirect user đến trang thanh toán
    private Instant createdAt;
    private Instant updatedAt;

    private Map<String, String> additionalData;
    private String message;
    private List<String> errors;
}
