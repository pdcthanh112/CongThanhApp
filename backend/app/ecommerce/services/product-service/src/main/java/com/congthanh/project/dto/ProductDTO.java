package com.congthanh.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String id;

  @NotNull
  private String name;

  private String category;

  private String subcategory;

  private String SKU;

  private String slug;

  private String description;

  private List<ProductImageDTO> image;

  private List<ProductAttributeValueDTO> attribute;

  private String supplier;

  private String brand;

  private List<ProductVariantDTO> variant;

  private String status;

}
