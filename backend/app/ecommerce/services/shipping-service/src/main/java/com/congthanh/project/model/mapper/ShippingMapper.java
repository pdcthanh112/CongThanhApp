package com.congthanh.project.model.mapper;

import com.congthanh.project.model.dto.ShippingDTO;
import com.congthanh.project.model.entity.Shipping;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ShippingMapper {

    private static  final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {

//        modelMapper.typeMap(CartItem.class, CartItemDTO.class)
//                .addMapping(src -> productMapper.mapProductEntityToDTO(src.getProduct()), CartItemDTO::setProduct)
//                .addMapping(src -> cartMapper.mapCartEntityToDTO(src.getCart()), CartItemDTO::setCart);

    }

    public static Shipping mapCartItemDTOToEntity(ShippingDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, Shipping.class);
    }

    public static ShippingDTO mapCartItemEntityToDTO(Shipping cartItem) {
        return modelMapper.map(cartItem, ShippingDTO.class);
    }
}
