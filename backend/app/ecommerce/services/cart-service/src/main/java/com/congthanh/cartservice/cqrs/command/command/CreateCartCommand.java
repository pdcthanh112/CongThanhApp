package com.congthanh.cartservice.cqrs.command.command;

import com.congthanh.cartservice.constant.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCartCommand {
    private Long id;
    private String name;
    private String customerId;
    private Instant createdAt;
    private Instant updatedAt;
    private CartStatus status;
    private boolean isDefault;
}
