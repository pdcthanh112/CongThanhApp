package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.entity.ecommerce.Product;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

        modelMapper.typeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getCategory().getName(), ProductDTO::setCategory)
                .addMapping(src -> src.getSubcategory().getName(), ProductDTO::setSubcategory)
                .addMapping(src -> src.getStore().getId(), ProductDTO::setStore);

        modelMapper.typeMap(ProductDTO.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setCategory);
                    mapper.skip(Product::setSubcategory);
                    mapper.skip(Product::setStore);
                });
//                .addMapping(dest -> dest.getCategory().setName(dest.getCategory()), Product::setCategory)
//                .addMapping(dest -> dest.getSubcategory().setName(dest.getSubcategory()), Product::setSubcategory)
//                .addMapping(dest -> dest.getStore().setName(dest.getStore()), Product::setStore);

    }

    public Product mapProductDTOToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO mapProductEntityToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}


