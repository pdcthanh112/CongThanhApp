package com.congthanh.cartservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String customerId;

    private boolean isDefault;

}
