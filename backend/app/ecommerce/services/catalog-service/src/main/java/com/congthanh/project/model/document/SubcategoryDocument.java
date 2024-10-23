package com.congthanh.project.model.document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class SubcategoryDocument {

    private Long id;

    @NotNull
    private String name;

    private String slug;

    private String description;

    private String image;

}
