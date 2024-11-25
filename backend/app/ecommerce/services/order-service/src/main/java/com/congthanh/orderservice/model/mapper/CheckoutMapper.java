package com.congthanh.orderservice.model.mapper;

import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.entity.Checkout;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CheckoutMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {
//        modelMapper.typeMap(Checkout.class, CheckoutDTO.class)
//                .addMapping(src -> cartMapper.mapCartEntityToDTO(src.getCart()), CheckoutDTO::setCart);

    }

    public static Checkout mapCheckoutDTOToEntity(CheckoutDTO checkoutDTO) {
        return modelMapper.map(checkoutDTO, Checkout.class);
    }

    public static CheckoutDTO mapCheckoutEntityToDTO(Checkout checkout) {
        return modelMapper.map(checkout, CheckoutDTO.class);
    }
}
