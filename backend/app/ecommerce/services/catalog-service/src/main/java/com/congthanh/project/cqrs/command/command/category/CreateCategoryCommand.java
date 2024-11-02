package com.congthanh.project.cqrs.command.command.category;

import com.congthanh.project.constant.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CreateCategoryCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String name;

    private final String slug;

    private final String description;

    private final String image;

    private final String parentId;

    private final CategoryStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
