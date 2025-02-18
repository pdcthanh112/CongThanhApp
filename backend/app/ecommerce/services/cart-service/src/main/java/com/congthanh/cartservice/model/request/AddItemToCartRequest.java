package com.congthanh.cartservice.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToCartRequest {

    @NotNull
    private String productId;

    @NotNull
    private String productQuantityId;

    @NotNull
    @Min(1)
    private int quantity;
}
