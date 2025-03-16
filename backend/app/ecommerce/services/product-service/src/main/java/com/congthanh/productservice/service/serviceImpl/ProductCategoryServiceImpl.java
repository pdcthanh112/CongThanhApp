package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.catalogservice.grpc.CategoryResponse;
import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.grpc.client.CategoryGrpcClient;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.ProductCategory;
import com.congthanh.productservice.repository.productCategory.ProductCategoryRepository;
import com.congthanh.productservice.service.ProductCategoryService;
import com.congthanh.productservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final CategoryGrpcClient categoryGrpcClient;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public void addCategoryToProduct(String categoryId, String productId) {
        CategoryResponse checkCategory = categoryGrpcClient.getCategoryById(categoryId);
        if (checkCategory == null) {
            throw new NotFoundException("Category not exist");
        }
        ProductCategory productCategory = productCategoryRepository.findProductCategoriesByCategoryIdAndProductId(categoryId, productId);
        if (productCategory != null) {
            throw new RuntimeException("Category already exits");
        }

        ProductCategory newProductCategory = ProductCategory.builder()
                .id(snowflakeIdGenerator.nextId())
                .categoryId(categoryId)
                .product(Product.builder().id(productId).build())
                .build();

        productCategoryRepository.save(newProductCategory);
    }

    @Override
    public void addCategoryToProduct(List<String> categoryIds, String productId) {
        for (int i = 0; i < categoryIds.size(); i++) {
            ProductCategory productCategory = productCategoryRepository.findProductCategoriesByCategoryIdAndProductId(categoryIds.get(i), productId);
            if (productCategory != null) {
                throw new RuntimeException("Category already exits");
            }

            ProductCategory newProductCategory = ProductCategory.builder()
                    .id(snowflakeIdGenerator.nextId())
                    .categoryId(categoryIds.get(i))
                    .product(Product.builder().id(productId).build())
                    .displayOrder(i)
                    .build();

            productCategoryRepository.save(newProductCategory);
        }
    }
}
