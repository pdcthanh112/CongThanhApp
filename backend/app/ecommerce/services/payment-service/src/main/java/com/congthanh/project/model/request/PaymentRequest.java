package com.congthanh.project.model.request;

import com.congthanh.project.constant.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class PaymentRequest {

    @NotNull
    private String orderId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String currency;

    @NotNull
    private PaymentMethod paymentMethod;

    private String description;
    private String returnUrl;
    private String cancelUrl;

    // Card payment specific fields
    private CardDetails cardDetails;

    // Bank transfer specific fields
    private BankDetails bankDetails;

    // COD specific fields
    private DeliveryAddress deliveryAddress;

    // Additional metadata
    private Map<String, String> metadata;
}
