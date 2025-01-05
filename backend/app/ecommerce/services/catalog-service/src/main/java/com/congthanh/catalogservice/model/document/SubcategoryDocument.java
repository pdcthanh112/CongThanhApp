package com.congthanh.catalogservice.model.document;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryDocument {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    private String description;

    private String image;

    private Set<SubcategoryDocument> children;

    @Enumerated
    private CategoryStatus status;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

}
