package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.viewmodel.ProductOptionValueGetVm;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.repository.variantOptionValue.VariantOptionValueRepository;
import com.congthanh.productservice.service.VariantOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantOptionServiceImpl implements VariantOptionService {

    private final ProductRepository productRepository;

    private final VariantOptionValueRepository productOptionValueRepository;

    @Override
    public List<ProductOptionValueGetVm> getVariantOptionValueByProduct(String productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Not found"));
        List<ProductOptionValueGetVm> productVariations = productOptionValueRepository
                .findAllByProduct(product).stream()
                .map(ProductOptionValueGetVm::fromModel)
                .toList();
        return productVariations;
    }
}
