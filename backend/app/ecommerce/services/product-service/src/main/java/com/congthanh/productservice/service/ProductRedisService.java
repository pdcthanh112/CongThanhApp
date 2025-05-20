package com.congthanh.productservice.service;

import com.congthanh.productservice.model.viewmodel.ProductVm;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductRedisService {

    List<ProductVm> getAllProduct();

    ProductVm getProductBySlug(String slug);

    void saveAllProduct(List<ProductVm> products) throws JsonProcessingException;

    void clear();
}
