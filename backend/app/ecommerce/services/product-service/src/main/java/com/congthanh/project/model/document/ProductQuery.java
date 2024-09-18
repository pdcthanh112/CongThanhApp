package com.congthanh.project.model.document;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.dto.ProductVariantDTO;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuery {

    @Id
    private String id;

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
