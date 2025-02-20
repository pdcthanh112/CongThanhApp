package com.congthanh.cartservice.model.document;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDocument {

    @Id
    private Long id;

    private String name;

    private String customerId;

    private Instant createdAt;

    private Instant updatedAt;

    private CartStatus status;

    private Set<CartItemDocument> cartItems = new HashSet<>();

    @JsonProperty("isDefault")
    private boolean isDefault;

}
