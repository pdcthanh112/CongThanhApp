package com.congthanh.productservice.model.dto;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.attribute.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.variant.ProductVariantDTO;
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

  @NotNull
  private String slug;

  private String description;

  private List<ProductImageDTO> image;

  private List<ProductAttributeValueDTO> attribute;

  private String supplier;

  private String brand;

  private List<ProductVariantDTO> variant;

  private ProductStatus status;

}
