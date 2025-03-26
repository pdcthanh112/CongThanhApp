package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.ProductCategory;
import com.congthanh.productservice.model.entity.ProductImage;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.repository.productCategory.ProductCategoryRepository;
import com.congthanh.productservice.repository.productImage.ProductImageRepository;
import com.congthanh.productservice.service.ProductAttributeService;
import com.congthanh.productservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventHandler {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductAttributeService productAttributeService;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product mainProduct = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
//                .brand(event.getBrand())
                .build();
        productRepository.save(mainProduct);

        List<ProductCategory> productCategories = setProductCategories(event.getCategory(), mainProduct);
        productCategoryRepository.saveAll(productCategories);

//        List<ProductImage> productImages = setProductImages(event.getImage(), mainProduct);
//        productImageRepository.saveAll(productImages);

    }

    private List<ProductCategory> setProductCategories(List<String> categoryIds, Product product) {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            List<String> listCategory
                    = product.getCategory().stream().map(ProductCategory::getCategoryId).sorted().toList();
            if (!CollectionUtils.isEqualCollection(listCategory, categoryIds.stream().sorted().toList())) {
                int index = 1;
                for (String categoryId : categoryIds) {
                    productCategoryList.add(ProductCategory.builder()
                            .id(snowflakeIdGenerator.nextId())
                            .categoryId(categoryId)
                            .product(product)
                            .displayOrder(index)
                            .build());
                    index++;
                }
            }
        }
        return productCategoryList;
    }

    private List<ProductImage> setProductImages(List<Long> imageMediaIds, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        if (CollectionUtils.isEmpty(imageMediaIds)) {
            productImageRepository.deleteByProductId(product.getId());
            return productImages;
        }
        if (product.getImage() == null) {
            productImages = imageMediaIds.stream()
                    .map(id -> ProductImage.builder().id(id).product(product).build()).toList();
        } else {
            List<Long> productImageIds = product.getImage().stream().map(ProductImage::getId).toList();
            List<Long> newImageIds = imageMediaIds.stream().filter(id -> !productImageIds.contains(id)).toList();
            List<Long> deletedImageIds = productImageIds.stream().filter(id -> !imageMediaIds.contains(id)).toList();
            if (CollectionUtils.isNotEmpty(newImageIds)) {
                productImages = newImageIds.stream()
                        .map(id -> ProductImage.builder().id(id).product(product).build()).toList();
            }
            if (CollectionUtils.isNotEmpty(deletedImageIds)) {
//                productImageRepository.deleteByImageIdInAndProductId(deletedImageIds, product.getId());
            }
        }
        return productImages;
    }

    @ResetHandler
    public void reset() {
        productRepository.deleteAllInBatch();
    }

}
