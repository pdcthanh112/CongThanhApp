package com.congthanh.customerservice.model.mapper;

import com.congthanh.customerservice.model.dto.ShippingAddressDTO;
import com.congthanh.customerservice.model.entity.ShippingAddress;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static ShippingAddress mapAddressDTOToEntity(ShippingAddressDTO shippingAddressDTO) {
        return modelMapper.map(shippingAddressDTO, ShippingAddress.class);
    }

    public static ShippingAddressDTO mapAddressEntityToDTO(ShippingAddress shippingAddress) {
        return modelMapper.map(shippingAddress, ShippingAddressDTO.class);
    }
}