package com.congthanh.catalogservice.cqrs.commands.command.category;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

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

    private final Instant createdAt;

    private final Instant updatedAt;

    private final CategoryStatus status;

}
