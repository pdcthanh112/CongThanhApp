package com.congthanh.cartservice.cqrs.command.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartCommand {
    private Long id;
    
}
