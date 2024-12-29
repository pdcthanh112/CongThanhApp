package com.congthanh.productservice.model.request;

import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import com.congthanh.productservice.model.dto.ProductVariantDTO;
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
    private String category;

    @NotNull
    private String slug;

    private String description;

    private String supplier;

    private String brand;

    private List<ProductImageDTO> image;

    private List<ProductAttributeValueDTO> attribute;

    private List<ProductVariantDTO> variant;

}
