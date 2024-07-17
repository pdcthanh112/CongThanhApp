package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.model.ecommerce.response.ProductVariantAttributeValueResponse;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.ecommerce.Product;

import java.util.List;

public interface ProductService {

  Object getAllProduct(Integer page, Integer limit);

  ProductDTO getProductById(String id);

  ProductDTO getProductDetailById(String id);

  ProductDTO getProductBySlug(String slug);

  ProductDTO createProduct(ProductDTO productDTO);

  Product updateProduct(ProductDTO productDTO);

  boolean deleteProduct(String id);

  ResponseWithPagination<ProductDTO> getProductByCategory(int categoryId, int page, int limit);

  ResponseWithPagination<ProductDTO> getProductBySubcategory(int subcategoryId, int page, int limit);

  List<ProductDTO> searchProduct(String keyword);

  Long getSoldByProduct(String productId);

  List<ProductVariantAttributeValueResponse> getVariantAttributeValueByProduct(String productId);
}
