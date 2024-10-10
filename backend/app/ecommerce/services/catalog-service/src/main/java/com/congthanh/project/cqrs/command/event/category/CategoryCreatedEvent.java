package com.congthanh.project.cqrs.command.event.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryCreatedEvent {

    private final Long id;

    private final String name;

    private final String slug;

    private final String description;

    private final String image;

    private final String status;

}
