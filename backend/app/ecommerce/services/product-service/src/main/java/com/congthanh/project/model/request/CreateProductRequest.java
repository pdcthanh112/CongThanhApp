package com.congthanh.project.model.request;

import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.dto.ProductVariantDTO;
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
public class CreateProductRequest {

    @NotNull
    private String name;

    @NotNull
    private Integer category;

    @NotNull
    private Integer subcategory;

    @NotNull
    private String slug;

    private String description;

    private List<ProductImageDTO> image;

    private List<ProductAttributeValueDTO> attribute;

    private String supplier;

    private String brand;

    private List<ProductVariantDTO> variant;

}
