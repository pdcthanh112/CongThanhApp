package com.congthanh.paymentservice.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class RefundRequest {
    @NotNull
    private String paymentId;

    @NotNull
    private BigDecimal amount;

    private String reason;
    private Map<String, String> metadata;
}
