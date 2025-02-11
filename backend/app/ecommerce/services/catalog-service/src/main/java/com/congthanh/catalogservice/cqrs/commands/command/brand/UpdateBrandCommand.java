package com.congthanh.catalogservice.cqrs.commands.command.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class UpdateBrandCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String slug;

    private String image;

}
