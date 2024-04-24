package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductImageDTO;
import com.congthanh.project.entity.ecommerce.ProductImage;
import com.congthanh.project.model.ecommerce.mapper.ProductImageMapper;
import com.congthanh.project.repository.ecommerce.productImage.ProductImageRepository;
import com.congthanh.project.service.ecommerce.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImageDTO> getImageByProduct(String productId) {
        List<ProductImage> data = productImageRepository.getImageByProduct(productId);
        List<ProductImageDTO> result = new ArrayList<>();
        for (ProductImage item : data) {
            ProductImageDTO imageDTO = productImageMapper.mapProductImageEntityToDTO(item);
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
        return productImageMapper.mapProductImageEntityToDTO(data);
    }

    @Override
    public ProductImageDTO addProductImage(ProductImageDTO productImageDTO) {
        ProductImage image = ProductImage.builder()
                .product(productImageDTO.getProduct())
                .imagePath(productImageDTO.getImagePath())
                .alt(productImageDTO.getAlt())
                .isDefault(productImageDTO.isDefault())
                .build();
        ProductImage result = productImageRepository.save(image);
        return productImageMapper.mapProductImageEntityToDTO(result);
    }
}
