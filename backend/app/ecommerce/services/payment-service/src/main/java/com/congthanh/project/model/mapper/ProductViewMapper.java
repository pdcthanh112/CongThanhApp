package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.ProductViewDTO;
import com.congthanh.project.entity.ecommerce.ProductView;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProductViewMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }


    @PostConstruct
    private void configureModelMapper() {

    }

    public static ProductViewDTO mapProductViewEntityToDTO(ProductView productView) {
        return modelMapper.map(productView, ProductViewDTO.class);
    }

    public static ProductView mapProductViewDTOToProductView(ProductViewDTO productViewDTO) {
        return modelMapper.map(productViewDTO, ProductView.class);
    }

}
