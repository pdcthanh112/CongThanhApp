package com.congthanh.cartservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private String sku;

    private String slug;

    private String description;

    private List<ProductImageResponse> image;

    private List<ProductAttributeValueResponse> attribute;

    private String supplier;

    private String brand;

    private List<ProductVariantResponse> variant;

    private String status;

}
