package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.CheckoutDTO;
import com.congthanh.project.entity.ecommerce.Checkout;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartMapper cartMapper;

    @PostConstruct
    private void configureModelMapper() {
//        modelMapper.typeMap(Checkout.class, CheckoutDTO.class)
//                .addMapping(src -> cartMapper.mapCartEntityToDTO(src.getCart()), CheckoutDTO::setCart);

    }

    public Checkout mapCheckoutDTOToEntity(CheckoutDTO checkoutDTO) {
        return modelMapper.map(checkoutDTO, Checkout.class);
    }

    public CheckoutDTO mapCheckoutEntityToDTO(Checkout checkout) {
        return modelMapper.map(checkout, CheckoutDTO.class);
    }
}
