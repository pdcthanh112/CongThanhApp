package com.congthanh.project.model.document;

import com.congthanh.project.constant.enums.CategoryStatus;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    private String description;

    private String image;

    @Enumerated
    private CategoryStatus status;

    private Set<SubcategoryDocument> subcategories;

}