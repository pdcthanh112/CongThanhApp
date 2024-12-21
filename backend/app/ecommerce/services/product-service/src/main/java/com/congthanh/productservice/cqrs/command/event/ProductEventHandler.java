package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventHandler {

    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .category(event.getCategory())
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
