package com.congthanh.project.service;

import com.congthanh.project.dto.ProductViewDTO;

public interface ProductViewService {

    Long getTotalViewOfProduct(String productId);

    ProductViewDTO addProductView(String productId, String customerId);

}
