package com.congthanh.cartservice.model.viewmodel;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartVm {

    private Long id;

    private String name;

    private String customerId;

    private Instant createdAt;

    private Instant updatedAt;

    private CartStatus status;

    private Set<CartItemVm> cartItems;

    @JsonProperty("isDefault")
    private boolean isDefault;
}
