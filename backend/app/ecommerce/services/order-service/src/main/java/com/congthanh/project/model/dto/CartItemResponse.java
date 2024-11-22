package com.congthanh.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private String id;

    private String product;

    private int quantity;

    private CartResponse cart;

    private long createdDate;

}
