package com.congthanh.project.model.document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDocument {

    private String id;

    @NotNull
    private String name;

    private String slug;

    private String description;

    private String image;

    private String status;

    Set<SubcategoryDocument> subcategories;

}
