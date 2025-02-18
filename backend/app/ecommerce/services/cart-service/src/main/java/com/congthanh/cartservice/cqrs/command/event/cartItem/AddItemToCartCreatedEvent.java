package com.congthanh.cartservice.cqrs.command.event.cartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String productId;
    private String productVariantId;
    private int quantity;
    private Long cartId;
    private Instant createdAt;
}
