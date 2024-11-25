package com.congthanh.catalogservice.cqrs.command.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class DeleteCategoryCommand {

    @TargetAggregateIdentifier
    private final String categoryId;

}
