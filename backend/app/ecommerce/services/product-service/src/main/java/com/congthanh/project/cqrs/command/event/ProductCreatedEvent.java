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

    private String name;

    private String category;

    private String subcategory;

    private String slug;

    private String description;

    private List<ProductImageDTO> image;

    private List<ProductAttributeValueDTO> attribute;

    private String supplier;

    private String brand;

    private List<ProductVariantDTO> variant;

    private ProductStatus status;

}
