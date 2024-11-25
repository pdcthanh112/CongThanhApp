package com.congthanh.paymentservice.model.request;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
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

//    // Card payment specific fields
//    private CardDetails cardDetails;
//
//    // Bank transfer specific fields
//    private BankDetails bankDetails;

    private Map<String, String> additionalInfo;
}
