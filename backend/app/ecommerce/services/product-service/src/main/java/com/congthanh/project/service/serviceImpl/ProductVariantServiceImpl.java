package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.dto.ProductVariantDTO;
import com.congthanh.project.entity.ProductVariant;
import com.congthanh.project.model.mapper.ProductVariantMapper;
import com.congthanh.project.model.request.ProductVariantRequest;
import com.congthanh.project.repository.product.ProductRepository;
import com.congthanh.project.repository.productVariant.ProductVariantRepository;
import com.congthanh.project.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    private final ProductRepository productRepository;

    private final KafkaTemplate<String, ProductVariantDTO> kafkaTemplate;

    @Override
    public List<ProductVariantDTO> getProductVariantByProductId(String productId) {
        List<ProductVariant> result = productRepository.getVariantByProductId(productId);
        List<ProductVariantDTO> response = new ArrayList<>();
        if (!result.isEmpty()) {
            for (ProductVariant item : result) {
                ProductVariantDTO productVariantDTO = ProductVariantDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .build();
                response.add(productVariantDTO);
            }
        }
        return response;
    }

    @Override
    @Transactional
    public ProductVariantDTO createProductVariant(ProductVariantRequest requestDTO) {

        ProductVariant variant = ProductVariant.builder().build();
        ProductVariant result = productVariantRepository.save(variant);
        ProductVariantDTO response = ProductVariantMapper.mapProductVariantEntityToDTO(result);

        kafkaTemplate.send("create-product-variant-topic", response);

        return response;
    }

    @KafkaListener(topics = "create-product-topic")
    private void handleCreateVariant(ProductDTO product) {
        ProductVariantRequest data = ProductVariantRequest.builder().
                product(product.getId())
                .build();
        this.createProductVariant(data);

    }


}
