package com.congthanh.paymentservice.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardTokenizeRequest {

    @NotNull
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$")
    private String expiryDate;

    @NotNull
    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv;

    @NotNull
    private String holderName;

}
