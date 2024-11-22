package com.congthanh.project.cqrs.command.command;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.model.dto.ProductAttributeValueDTO;
import com.congthanh.project.model.dto.ProductImageDTO;
import com.congthanh.project.model.dto.ProductVariantDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String name;

    private final String category;

    private final Long subcategory;

    private final String slug;

    private final String description;

    private final List<ProductImageDTO> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final String supplier;

    private final String brand;

    private final List<ProductVariantDTO> variant;

    private final ProductStatus status;

}
