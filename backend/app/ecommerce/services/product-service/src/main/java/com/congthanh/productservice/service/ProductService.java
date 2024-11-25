package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.request.CreateProductRequest;
import com.congthanh.productservice.model.dto.ProductVariantAttributeValueDTO;
import com.congthanh.productservice.model.entity.Product;

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

  List<ProductVariantAttributeValueDTO> getVariantAttributeValueByProduct(String productId);

}
