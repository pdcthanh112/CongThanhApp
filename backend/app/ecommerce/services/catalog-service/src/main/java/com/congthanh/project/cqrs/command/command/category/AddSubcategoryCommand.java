package com.congthanh.project.cqrs.command.command.category;

import com.congthanh.project.constant.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class AddSubcategoryCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String name;

    private final String slug;

    private final String description;

    private final String image;

    private final String parentId;

    private final CategoryStatus status;

}
