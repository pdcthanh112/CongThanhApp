package com.congthanh.cartservice.model.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDetailVm {
    private Long id;
    private int quantity;
    private Product product;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Product {
        private String id;
        private String name;
        private String slug;
        private String image;
    }
}
