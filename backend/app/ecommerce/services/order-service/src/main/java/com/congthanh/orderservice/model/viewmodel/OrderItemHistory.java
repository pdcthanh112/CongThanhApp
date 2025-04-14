package com.congthanh.orderservice.model.viewmodel;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemHistory(
        Long id,
        ProductVm product,
        int quantity,
        BigDecimal orderPrice) {

    @Builder
     public record ProductVm(String productId, String productName, String slug, String thumbnailUrl) {
    }

}
