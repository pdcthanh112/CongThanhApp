package com.congthanh.catalogservice.cqrs.commands.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class RemoveSubcategoryCommand {

    @TargetAggregateIdentifier
    private final String categoryId;

    private final String parentId;

}
