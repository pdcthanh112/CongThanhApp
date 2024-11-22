package com.congthanh.project.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    private Long id;

    @NotNull
    private String customer;

    @NotNull
    private String phone;

    private String country;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String street;

    private String postalCode;

    @JsonProperty("isDefault")
    private boolean isDefault;

}