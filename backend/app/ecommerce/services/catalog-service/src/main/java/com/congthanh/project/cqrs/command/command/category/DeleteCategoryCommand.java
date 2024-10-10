package com.congthanh.project.cqrs.command.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeleteCategoryCommand {

    private final Long categoryId;

}
