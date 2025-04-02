package com.congthanh.cartservice.model.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemVm {

    private Long id;

    private String productId;

    private int quantity;

    private Instant createdAt;
}
