package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.CartItemDTO;
import com.congthanh.project.entity.ecommerce.CartItem;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;

    @PostConstruct
    private void configureModelMapper() {

//        modelMapper.typeMap(CartItem.class, CartItemDTO.class)
//                .addMapping(src -> productMapper.mapProductEntityToDTO(src.getProduct()), CartItemDTO::setProduct)
//                .addMapping(src -> cartMapper.mapCartEntityToDTO(src.getCart()), CartItemDTO::setCart);

    }

    public CartItem mapCartItemDTOToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    public CartItemDTO mapCartItemEntityToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}
