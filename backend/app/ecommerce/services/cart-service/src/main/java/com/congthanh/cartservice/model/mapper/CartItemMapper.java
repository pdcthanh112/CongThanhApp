package com.congthanh.cartservice.model.mapper;

import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.entity.CartItem;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

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

    public static CartItem mapCartItemDTOToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    public static CartItemDTO mapCartItemEntityToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}
