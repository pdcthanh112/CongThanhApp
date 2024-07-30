package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.ecommerce.ProductImageDTO;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.ProductImage;
import com.congthanh.project.exception.NotFoundException;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.productImage.ProductImageRepository;
import com.congthanh.project.service.ProductImageService;
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
    public ProductImageDTO getDefaultImageByProduct(String productId) {
        ProductImage data = productImageRepository.getDefaultImageByProduct(productId);
        if (data == null) {
            return null;
        }
        return ProductImageMapper.mapProductImageEntityToDTO(data);
    }

    @Override
    public ProductImageDTO addProductImage(ProductImageDTO productImageDTO) {
        Product product = productRepository.findById(productImageDTO.getProduct()).orElseThrow(() -> new NotFoundException(("not found")));
        ProductImage image = ProductImage.builder()
                .product(product)
                .imagePath(productImageDTO.getImagePath())
                .alt(productImageDTO.getAlt())
                .isDefault(productImageDTO.isDefault())
                .build();
        ProductImage result = productImageRepository.save(image);
        return ProductImageMapper.mapProductImageEntityToDTO(result);
    }
}
