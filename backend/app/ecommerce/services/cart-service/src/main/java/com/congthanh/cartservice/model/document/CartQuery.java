package com.congthanh.cartservice.model.document;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartQuery {

    @Id
    private String id;

    private String name;

    private String customer;

    private Instant createdAt;

    private Instant updatedAt;

    private CartStatus status;

    private Set<CartItemDTO> cartItems;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
