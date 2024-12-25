package com.congthanh.catalogservice.model.dto;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
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
public class CategoryDTO {

  private String id;

  @NotNull
  private String name;

  private String slug;

  private String description;

  private String image;

  private String parentId;

  private Instant createdAt;

  private String createdBy;

  private Instant updatedAt;

  private String updatedBy;

  private CategoryStatus status;

  private Set<CategoryDTO> children;

}
