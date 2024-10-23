package com.congthanh.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryDTO {

    private Long id;

    @NotNull
    private String name;

    private String slug;

    private String description;

    private String image;

    private CategoryDTO category;

}