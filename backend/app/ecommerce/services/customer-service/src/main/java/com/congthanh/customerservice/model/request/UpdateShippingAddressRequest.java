package com.congthanh.customerservice.model.request;

import com.congthanh.customerservice.constant.enums.AddressLabel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateShippingAddressRequest {

    private String customer;

    private String fullName;

    private String phone;

    private AddressLabel label;

    private String country;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String street;

    private String postalCode;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
