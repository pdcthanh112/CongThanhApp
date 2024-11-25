package com.congthanh.searchservice.model.response;

import com.congthanh.searchservice.model.document.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductGetVm(String id,
                           String name,
                           String slug,
                           Long thumbnailId,
                           BigDecimal price,
                           Boolean isAllowedToOrder,
                           Boolean isPublished,
                           Boolean isFeatured,
                           Boolean isVisibleIndividually,
                           Instant createdOn) {
    public static ProductGetVm fromModel(Product product) {
        return new ProductGetVm(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getThumbnailMediaId(),
                product.getPrice(),
                product.getIsAllowedToOrder(),
                product.getIsPublished(),
                product.getIsFeatured(),
                product.getIsVisibleIndividually(),
                product.getCreatedOn()
        );
    }
}
