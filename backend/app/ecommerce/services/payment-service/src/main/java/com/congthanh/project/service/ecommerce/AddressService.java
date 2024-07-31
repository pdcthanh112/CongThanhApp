package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO getAddressById(Long addressId);

    AddressDTO createAddress(AddressDTO addressDTO);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    boolean deleteAddress(Long addressId);

    List<AddressDTO> getAddressByCustomer(String customerId);

    AddressDTO getDefaultAddressOfCustomer(String customerId);

    boolean setDefaultAddressForCustomer(String customerId, Long addressId);

}
