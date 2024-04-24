package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.CartDTO;
import com.congthanh.project.entity.ecommerce.Cart;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Cart mapCartDTOToEntity(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    public CartDTO mapCartEntityToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }

}
