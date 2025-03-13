package com.congthanh.productservice.cqrs.command.command;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.model.dto.attribute.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.variant.ProductVariantDTO;
import com.congthanh.productservice.model.request.ProductImageRequest;
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

    private final List<String> category;

    private final String slug;

    private final String thumbnail;

    private final String description;

    private final List<ProductImageRequest> image;

    private final List<ProductAttributeValueDTO> attribute;

    private final String supplier;

    private final String brand;

    private final List<ProductVariantDTO> variant;

    private final ProductStatus status;

}
