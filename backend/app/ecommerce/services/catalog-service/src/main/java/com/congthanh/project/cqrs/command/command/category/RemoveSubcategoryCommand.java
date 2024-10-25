package com.congthanh.project.cqrs.command.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RemoveSubcategoryCommand {

    private final String categoryId;

    private final String parentId;

}
