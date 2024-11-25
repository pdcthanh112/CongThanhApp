package com.congthanh.customerservice.model.mapper;

import com.congthanh.customerservice.model.dto.AddressDTO;
import com.congthanh.customerservice.model.entity.Address;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Address mapAddressDTOToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public static AddressDTO mapAddressEntityToDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}