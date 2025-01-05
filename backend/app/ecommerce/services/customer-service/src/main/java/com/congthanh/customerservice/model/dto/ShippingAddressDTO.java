package com.congthanh.customerservice.model.dto;

import com.congthanh.customerservice.constant.enums.AddressLabel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressDTO {

    private Long id;

    @NotNull
    private String customer;

    private String fullName;

    @NotNull
    private String phone;

    private AddressLabel label;

    private String country;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String street;

    private String postalCode;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @JsonProperty("isDefault")
    private boolean isDefault;

}