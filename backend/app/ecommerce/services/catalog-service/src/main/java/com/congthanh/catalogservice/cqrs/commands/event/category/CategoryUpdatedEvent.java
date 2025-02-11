package com.congthanh.catalogservice.cqrs.commands.event.category;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CategoryUpdatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
