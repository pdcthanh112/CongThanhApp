package com.congthanh.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {

    private String id;

    private String product;

    private String name;

    private BigDecimal price;

    private List<ProductVariantImageResponse> image;

}
