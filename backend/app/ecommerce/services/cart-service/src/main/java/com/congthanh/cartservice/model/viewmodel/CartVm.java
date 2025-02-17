package com.congthanh.cartservice.model.viewmodel;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class CartVm {

    private String id;

    private String name;

    private String customer;

    private Instant createdAt;

    private Instant updatedAt;

    private CartStatus status;

    private Set<CartItemVm> cartItems;

    @JsonProperty("isDefault")
    private boolean isDefault;
}
