package com.congthanh.cartservice.cqrs.command.event.cart;

import com.congthanh.cartservice.constant.enums.CartStatus;
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
public class CartCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String customerId;
    private Instant createdAt;
    private Instant updatedAt;
    private CartStatus status;
    private boolean isDefault;
}
