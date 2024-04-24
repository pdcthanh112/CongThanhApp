package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.AddressDTO;
import com.congthanh.project.entity.ecommerce.Address;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Address mapAddressDTOToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public AddressDTO mapAddressEntityToDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}
