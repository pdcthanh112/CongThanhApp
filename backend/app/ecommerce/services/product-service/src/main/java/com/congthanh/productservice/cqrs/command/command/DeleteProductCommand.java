package com.congthanh.productservice.cqrs.command.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class DeleteProductCommand {

    @TargetAggregateIdentifier
    private String productId;

}
