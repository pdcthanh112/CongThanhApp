package com.congthanh.project.cqrs.command.event.category;

import com.congthanh.project.constant.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

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

    private final CategoryStatus status;

}