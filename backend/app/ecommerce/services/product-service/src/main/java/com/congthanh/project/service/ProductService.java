package com.congthanh.project.service;

import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.model.request.CreateProductRequest;
import com.congthanh.project.dto.ProductVariantAttributeValueResponse;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.entity.Product;

import java.util.List;

public interface ProductService {

  Object getAllProduct(Integer page, Integer limit);

  ProductDTO getProductById(String id);

  ProductDTO getProductDetailById(String id);

  ProductDTO getProductBySlug(String slug);

  ProductDTO createProduct(CreateProductRequest request);

  Product updateProduct(ProductDTO productDTO);

  boolean deleteProduct(String id);

//  ResponseWithPagination<ProductDTO> getProductByCategory(int categoryId, int page, int limit);
//
//  ResponseWithPagination<ProductDTO> getProductBySubcategory(int subcategoryId, int page, int limit);

  List<ProductDTO> searchProduct(String keyword);

  Long getSoldByProduct(String productId);

  List<ProductVariantAttributeValueResponse> getVariantAttributeValueByProduct(String productId);
}
