package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.attribute.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import com.congthanh.productservice.model.dto.variant.ProductVariantDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductCreatedEvent {

    private final String id;

    private final String name;

    private final List<String> category;

    private final String slug;

    private final String description;

    private final List<ProductImageDTO> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final String supplier;

    private final String brand;

    private final List<ProductVariantDTO> variant;

    private final ProductStatus status;

}
