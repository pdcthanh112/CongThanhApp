package com.congthanh.catalogservice.cqrs.command.command.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class CreateBrandCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String image;

}
