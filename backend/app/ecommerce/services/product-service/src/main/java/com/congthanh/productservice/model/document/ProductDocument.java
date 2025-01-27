package com.congthanh.productservice.model.document;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {

    @Id
    private String id;

    @NotNull
    private String name;

    private CategoryDocument category;

    @NotNull
    private String slug;

    private String description;

    private List<ProductImageDTO> image = new ArrayList<>();

    private List<ProductAttributeValueDTO> attribute = new ArrayList<>();

    private String supplier;

    private String brand;

    private List<ProductVariantDocument> variant = new ArrayList<>();

    private ProductStatus status;

}
