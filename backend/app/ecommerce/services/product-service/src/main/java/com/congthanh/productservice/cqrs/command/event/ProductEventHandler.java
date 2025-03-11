package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.service.ProductAttributeService;
import com.congthanh.productservice.service.ProductCategoryService;
import com.congthanh.productservice.service.ProductVariantService;
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

    private final ProductCategoryService productCategoryService;

    private final ProductAttributeService productAttributeService;

    private final ProductVariantService productVariantService;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
//                .brand(event.getBrand())
                .build();
        productRepository.save(product);

        productCategoryService.addCategoryToProduct(event.getCategory(), event.getId());


    }

    @ResetHandler
    public void reset() {
        productRepository.deleteAllInBatch();
    }

}
