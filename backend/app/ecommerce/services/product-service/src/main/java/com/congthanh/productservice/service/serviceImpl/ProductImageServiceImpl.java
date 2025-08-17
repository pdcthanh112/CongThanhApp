package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.productservice.model.dto.ProductImageDTO;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.mapper.ProductImageMapper;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.repository.productImage.ProductImageRepository;
import com.congthanh.productservice.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductImageDTO> getImageByProduct(String productId) {
        List<ProductImage> data = productRepository.getImageByProduct(productId);
        List<ProductImageDTO> result = new ArrayList<>();
        for (ProductImage item : data) {
            ProductImageDTO imageDTO = ProductImageMapper.mapProductImageEntityToDTO(item);
            result.add(imageDTO);
        }
        return result;
    }

    @Override
    public ProductImageDTO addProductImage(ProductImageDTO productImageDTO) {
        Product product = productRepository.findById(productImageDTO.getProduct()).orElseThrow(() -> new NotFoundException(("not found")));
        ProductImage image = ProductImage.builder()
                .product(product)
                .imagePath(productImageDTO.getImagePath())
                .build();
        ProductImage result = productImageRepository.save(image);
        return ProductImageMapper.mapProductImageEntityToDTO(result);
    }
}
