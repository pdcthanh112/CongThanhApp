package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductViewDTO;

public interface ProductViewService {

    Long getTotalViewOfProduct(String productId);

    ProductViewDTO addProductView(String productId, String customerId);

}
