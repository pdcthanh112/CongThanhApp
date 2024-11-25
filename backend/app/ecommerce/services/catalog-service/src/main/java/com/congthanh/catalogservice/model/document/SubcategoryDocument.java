package com.congthanh.catalogservice.model.document;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

//@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryDocument {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    private String description;

    private String image;

    @Enumerated
    private CategoryStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
