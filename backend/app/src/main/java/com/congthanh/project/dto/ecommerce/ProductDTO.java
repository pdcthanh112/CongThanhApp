package com.congthanh.project.dto.ecommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

  private String id;

  @NotNull
  private String name;

  private String category;

  private String subcategory;

  private String SKU;

  @Min(value = 0)
  private int quantity;

  @Min(value = 0)
  private BigDecimal price;

  private String production;

  private String description;

  private String store;

  private String status;

  private String slug;

}
