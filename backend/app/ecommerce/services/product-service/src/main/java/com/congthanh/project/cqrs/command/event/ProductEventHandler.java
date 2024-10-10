package com.congthanh.project.cqrs.command.event;

import com.congthanh.project.entity.Product;
import com.congthanh.project.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .category(event.getCategory())
                .subcategory(event.getSubcategory())
                .slug(event.getSlug())
                .description(event.getDescription())
//                .brand(event.getBrand())
                .build();
        productRepository.save(product);
    }

    @ResetHandler
    public void reset() {
        productRepository.deleteAllInBatch();
    }

}
