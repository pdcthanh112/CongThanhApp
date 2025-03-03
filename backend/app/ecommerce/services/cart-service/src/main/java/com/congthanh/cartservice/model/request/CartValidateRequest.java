package com.congthanh.cartservice.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartValidateRequest {

    private Long cartId;

    private List<CartItemValidateRequest> items;
}

@Data
@Builder
class CartItemValidateRequest {
    private Long itemId;
    private String variantId;
    private int quantity;
}
