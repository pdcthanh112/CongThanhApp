package com.congthanh.paymentservice.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDetails {

    @NotNull
    private String cardNumber;

    @NotNull
    private String cardHolderName;

    @NotNull
    private String expiryMonth;

    @NotNull
    private String expiryYear;

    @NotNull
    private String cvv;

    private String brand; // VISA, MASTERCARD, etc.

}
