package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.request.CreateProductRequest;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.response.ResponseWithPagination;
import com.congthanh.productservice.model.viewmodel.ProductVm;

import java.util.List;

public interface ProductService {

  Object getAllProduct(Integer page, Integer limit);

  ProductDTO getProductById(String id);

  ProductDTO getProductDetailById(String id);

  ProductDTO getProductDTOBySlug(String slug);

  ProductVm getProductVmBySlug(String slug);

  ProductDTO createProduct(CreateProductRequest request);

  Product updateProduct(ProductDTO productDTO);

  boolean deleteProduct(String id);

  ResponseWithPagination<ProductDTO> getProductByCategoryId(String categoryId, int page, int limit);
  ResponseWithPagination<ProductDTO> getProductByCategorySlug(String categoryId, int page, int limit);

  List<ProductDTO> searchProduct(String keyword);

  Long getSoldByProduct(String productId);

//  List<ProductVariantAttributeValueDTO> getVariantAttributeValueByProduct(String productId);

}
