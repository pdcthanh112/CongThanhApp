package com.congthanh.cartservice.model.document;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CartItemDocument {

    private Long id;

    private String productId;

    private int quantity;

    private Instant createdAt;
}
