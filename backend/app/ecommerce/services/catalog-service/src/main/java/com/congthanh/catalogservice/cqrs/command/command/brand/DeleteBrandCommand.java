package com.congthanh.catalogservice.cqrs.command.command.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class DeleteBrandCommand {

    @TargetAggregateIdentifier
    private String brandId;

}
