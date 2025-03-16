package com.congthanh.productservice.model.mapper;

import com.congthanh.productservice.model.dto.attribute.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import com.congthanh.productservice.model.dto.variant.ProductVariantDTO;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.attribute.ProductAttributeValue;
import com.congthanh.productservice.model.entity.ProductImage;
import com.congthanh.productservice.model.entity.ProductVariant;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {

        modelMapper.typeMap(Product.class, ProductDTO.class)
//                .addMapping(src -> src.getImage() != null ? src.getImage().stream().map(productImage -> modelMapper.map(productImage, ProductImageDTO.class))
//                        .collect(Collectors.toList()) : Collections.emptyList(), ProductDTO::setImage)
        ;


        modelMapper.typeMap(ProductDTO.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setCategory);
                    mapper.skip(Product::setSupplier);
                });
//                .addMapping(dest -> dest.getCategory().setName(dest.getCategory()), Product::setCategory)
//                .addMapping(dest -> dest.getSubcategory().setName(dest.getSubcategory()), Product::setSubcategory)
//                .addMapping(dest -> dest.getStore().setName(dest.getStore()), Product::setStore);

    }

    public static Product productDTOToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public static ProductDTO mapProductEntityToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setImage(mapProductImageListToProductImageDTOList(product.getImage()));
        productDTO.setAttribute(mapProductAttributeValueListToProductAttributeValueDTOList(product.getAttribute()));
        productDTO.setVariant(mapProductVariantListToProductVariantDTOList(product.getVariant()));
        return productDTO;
    }

    private static List<ProductImageDTO> mapProductImageListToProductImageDTOList(Set<ProductImage> productImages) {
        return productImages.stream()
                .map(productImage -> ProductImageMapper.mapProductImageEntityToDTO(productImage))
                .collect(Collectors.toList());
    }

    private static List<ProductAttributeValueDTO> mapProductAttributeValueListToProductAttributeValueDTOList(Set<ProductAttributeValue> productAttributeValues) {
        return productAttributeValues.stream()
                .map(productAttributeValue -> ProductAttributeValueMapper.mapProductAttributeValueEntityToDTO(productAttributeValue))
                .collect(Collectors.toList());
    }

    private static List<ProductVariantDTO> mapProductVariantListToProductVariantDTOList(Set<ProductVariant> productVariants) {
        return productVariants.stream()
                .map(productVariant -> ProductVariantMapper.mapProductVariantEntityToDTO(productVariant))
                .collect(Collectors.toList());
    }

}


