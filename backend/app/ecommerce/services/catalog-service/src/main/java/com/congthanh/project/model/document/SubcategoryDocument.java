package com.congthanh.project.model.document;

import com.congthanh.project.constant.enums.CategoryStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document
@Data
public class SubcategoryDocument {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    private String description;

    private String image;

    @Enumerated
    private CategoryStatus status;

}
