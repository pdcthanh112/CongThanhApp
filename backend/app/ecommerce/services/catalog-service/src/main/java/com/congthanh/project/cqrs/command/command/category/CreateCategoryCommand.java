package com.congthanh.project.cqrs.command.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class CreateCategoryCommand {

    @TargetAggregateIdentifier
    private final Long id;

    private final String name;

    private final String slug;

    private final String description;

    private final String image;

    private final String status;

}
