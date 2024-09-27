package com.congthanh.project.cqrs.command.event;

import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    private final ProductQueryRepository productQueryRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductQuery product = ProductQuery.builder()
                .id(event.getId())
                .name(event.getName())
                .category(event.getCategory())
                .subcategory(event.getSubcategory())
                .slug(event.getSlug())
                .description(event.getDescription())
                .brand(event.getBrand())
                .build();
        productQueryRepository.save(product);
    }

}
