package com.congthanh.project.service;

import com.congthanh.project.model.dto.AddressDTO;

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