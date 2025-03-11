package com.congthanh.productservice.model.dto.variant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String product;

    private String name;

    private String sku;

    private String gtin;

    private BigDecimal price;

    private List<ProductVariantImageDTO> image;

}
