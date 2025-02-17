package com.congthanh.cartservice.model.viewmodel;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CartItemVm {
    private Long id;

    private String productId;

    private int quantity;

    private Instant createdAt;
}
