package com.congthanh.project.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private String id;

    private String name;

    private String customer;

    private long createdDate;

    private String status;

    private Set<CartItemResponse> cartItems;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
