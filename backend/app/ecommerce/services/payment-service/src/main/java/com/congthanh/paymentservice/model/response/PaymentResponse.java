package com.congthanh.paymentservice.model.response;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.constant.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PaymentResponse {

    private String paymentId;

    private PaymentStatus status;

    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    private String currency;

    private String orderId;

    private String transactionId;

    private Instant createdAt;

    private Instant updatedAt;

    private Map<String, String> additionalInfo;

    private String message;

    private List<String> errors;

}
