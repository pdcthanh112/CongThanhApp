package com.congthanh.project.model.document;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.model.dto.ProductAttributeValueDTO;
import com.congthanh.project.model.dto.ProductImageDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {

    @Id
    private String id;

    private String name;

    private CategoryDocument category;

    @NotNull
    private String slug;

    private String description;

    private List<ProductImageDTO> image;

    private List<ProductAttributeValueDTO> attribute;

    private String supplier;

    private String brand;

    private List<ProductVariantDocument> variant;

    private ProductStatus status;

}

@Data
@Builder
class ProductVariantDocument {
    private String id;

    private String product;

    private String name;

    private String sku;

    private String gtin;

    private BigDecimal price;

    private List<ProductVariantImageDocument> image;

}

@Data
@Builder
class ProductVariantImageDocument {

    private Long id;

    private String variant;

    private String imagePath;

    private String alt;

    @JsonProperty("isDefault")
    private boolean isDefault;

}
