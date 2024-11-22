package com.congthanh.project.model.dto;

import com.congthanh.project.constant.enums.CategoryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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

  private Instant updatedAt;

  private CategoryStatus status;

}
