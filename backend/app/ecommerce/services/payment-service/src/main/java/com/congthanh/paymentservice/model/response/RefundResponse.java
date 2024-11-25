package com.congthanh.paymentservice.model.response;

import com.congthanh.paymentservice.constant.enums.RefundStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class RefundResponse {
    private String refundId;
    private String paymentId;
    private RefundStatus status;
    private BigDecimal amount;
    private String currency;
    private Instant processedAt;
    private String message;
}
