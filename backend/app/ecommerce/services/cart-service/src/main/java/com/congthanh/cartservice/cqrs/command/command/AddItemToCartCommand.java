package com.congthanh.cartservice.cqrs.command.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartCommand {

    private Long id;

    private Long cartId
            ;
    private String productId;

    private int quantity;

    private Instant createdAt;
}
