package com.congthanh.productservice.service;

import com.congthanh.productservice.model.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    void addCategoryToProduct(String categoryId, String productId);

    void addCategoryToProduct(List<String> categoryIds, String productId);
}
