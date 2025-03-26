package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductUpdatedEvent {

    private final String id;

    private final String name;

    private final String category;

    private final String slug;

    private final String supplier;

    private final String brand;

    private final String description;

    private final List<ProductImageDTO> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final ProductStatus status;

}
