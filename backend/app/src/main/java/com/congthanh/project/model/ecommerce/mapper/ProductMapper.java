package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ProductAttributeValueDTO;
import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.dto.ecommerce.ProductImageDTO;
import com.congthanh.project.dto.ecommerce.ProductVariantDTO;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.ProductAttributeValue;
import com.congthanh.project.entity.ecommerce.ProductImage;
import com.congthanh.project.entity.ecommerce.ProductVariant;
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
                .addMapping(src -> src.getCategory().getName(), ProductDTO::setCategory)
                .addMapping(src -> src.getSubcategory().getName(), ProductDTO::setSubcategory)
                .addMapping(src -> src.getSupplier().getName(), ProductDTO::setSupplier)
                .addMapping(src -> src.getBrand().getName(), ProductDTO::setBrand)
//                .addMapping(src -> src.getImage() != null ? src.getImage().stream().map(productImage -> modelMapper.map(productImage, ProductImageDTO.class))
//                        .collect(Collectors.toList()) : Collections.emptyList(), ProductDTO::setImage)
        ;


        modelMapper.typeMap(ProductDTO.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setCategory);
                    mapper.skip(Product::setSubcategory);
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


