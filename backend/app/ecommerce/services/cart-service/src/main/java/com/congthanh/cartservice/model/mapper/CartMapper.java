package com.congthanh.cartservice.model.mapper;

import com.congthanh.cartservice.model.document.CartDocument;
import com.congthanh.cartservice.model.dto.CartDTO;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.model.viewmodel.CartVm;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Cart mapCartDTOToEntity(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    public static CartDTO mapCartEntityToDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }

    public static CartVm mapCartDocumentToVm(CartDocument cart) {
        return modelMapper.map(cart, CartVm.class);
    }

}
