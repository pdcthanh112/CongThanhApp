package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.AddressDTO;
import com.congthanh.project.entity.Address;
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
