package com.congthanh.customerservice.service;

import com.congthanh.customerservice.model.dto.ShippingAddressDTO;
import com.congthanh.customerservice.model.request.CreateShippingAddressRequest;

import java.util.List;

public interface ShippingAddressService {

    ShippingAddressDTO getAddressById(Long addressId);

    ShippingAddressDTO createAddress(CreateShippingAddressRequest request);

    ShippingAddressDTO updateAddress(Long addressId, ShippingAddressDTO shippingAddressDTO);

    boolean deleteAddress(Long addressId);

    List<ShippingAddressDTO> getAddressByCustomer(String customerId);

    ShippingAddressDTO getDefaultAddressOfCustomer(String customerId);

    boolean setDefaultAddressForCustomer(String customerId, Long addressId);

}