package com.congthanh.project.cqrs.command.event;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.dto.ProductVariantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {

    private final String id;

    private final String name;

    private final String category;

    private final String subcategory;

    private final String slug;

    private final String description;

    private final List<ProductImageDTO> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final String supplier;

    private final String brand;

    private final List<ProductVariantDTO> variant;

    private final ProductStatus status;

}
