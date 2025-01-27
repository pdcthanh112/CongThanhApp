package com.congthanh.productservice.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDocument {

    private String id;

    private String product;

    private String name;

    private String sku;

    private String gtin;

    private BigDecimal price;

    private List<ProductVariantImageDocument> image = new ArrayList<>();

}
