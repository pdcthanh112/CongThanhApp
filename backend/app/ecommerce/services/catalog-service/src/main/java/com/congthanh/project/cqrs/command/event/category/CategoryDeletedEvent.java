package com.congthanh.project.cqrs.command.event.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryDeletedEvent {

    private final String id;

}
