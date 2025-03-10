package com.congthanh.productservice.model.viewmodel;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProductVm {
    private String id;
    private String name;
    private CategoryVm category;
    private String slug;
    private String description;
    private List<ProductImageDTO> image;
    private List<ProductAttributeValueDTO> attribute;
    private String supplier;
    private String brand;
    private List<ProductVariantVm> variant;
    private ProductStatus status;
}


record CategoryVm(
        String id,
        String name,
        Set<CategoryVm> children) {
}

record ProductVariantVm(String id,
                        String product,
                        String name,
                        String sku,
                        String gtin,
                        BigDecimal price,
                        List<ProductVariantImageVm> image) {
}

record ProductVariantImageVm(Long id,
                             String variant,
                             String imagePath,
                             String alt,
                             boolean isDefault) {
}
