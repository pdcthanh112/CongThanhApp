package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
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

    private final String thumbnail;

    private final String description;

    private final List<ProductImage> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final String supplier;

    private final String brand;

    private final List<Long> tag;

    private final ProductStatus status;

    record ProductImage(String imagePath, int displayOrder) {}

}
