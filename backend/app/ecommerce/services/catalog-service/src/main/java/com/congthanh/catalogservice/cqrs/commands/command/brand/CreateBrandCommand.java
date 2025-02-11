package com.congthanh.catalogservice.cqrs.commands.command.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class CreateBrandCommand {

    @TargetAggregateIdentifier
    private Long id;

    private String name;

    private String slug;

    private String image;

}
