package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.AddressDTO;
import com.congthanh.project.entity.Address;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.AddressMapper;
import com.congthanh.project.repository.address.AddressRepository;
import com.congthanh.project.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address result = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("address not found"));
        return AddressMapper.mapAddressEntityToDTO(result);
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .customer(addressDTO.getCustomer())
                .phone(addressDTO.getPhone())
                .country(addressDTO.getCountry())
                .addressLine1(addressDTO.getAddressLine1())
                .addressLine2(addressDTO.getAddressLine2())
                .addressLine3(addressDTO.getAddressLine3())
                .street(addressDTO.getStreet())
                .postalCode(addressDTO.getPostalCode())
                .isDefault(addressDTO.isDefault())
                .build();
        Address result = addressRepository.save(address);
        if (addressDTO.isDefault()) {
            this.setDefaultAddressForCustomer(addressDTO.getCustomer(), result.getId());
        }
        return AddressMapper.mapAddressEntityToDTO(result);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address exitsAddress = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("address not found"));
        exitsAddress.setPhone(addressDTO.getPhone());
        exitsAddress.setCountry(addressDTO.getCountry());
        exitsAddress.setAddressLine1(addressDTO.getAddressLine1());
        exitsAddress.setAddressLine2(addressDTO.getAddressLine2());
        exitsAddress.setAddressLine3(addressDTO.getAddressLine3());
        exitsAddress.setStreet(addressDTO.getStreet());
        exitsAddress.setStreet(addressDTO.getStreet());
        exitsAddress.setPostalCode(addressDTO.getPostalCode());
        Address result = addressRepository.save(exitsAddress);
        return AddressMapper.mapAddressEntityToDTO(result);
    }

    @Override
    public boolean deleteAddress(Long addressId) {
        try {
            addressRepository.deleteById(addressId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<AddressDTO> getAddressByCustomer(String customerId) {
        List<Address> data = addressRepository.getAddressByCustomerId(customerId);
        if (!data.isEmpty()) {
            List<AddressDTO> result = new ArrayList<>();
            for (Address item : data) {
                AddressDTO address = AddressMapper.mapAddressEntityToDTO(item);
                result.add(address);
            }
            return result;
        }
        return null;
    }

    @Override
    public AddressDTO getDefaultAddressOfCustomer(String customerId) {
        Address data = addressRepository.getDefaultAddressOfCustomer(customerId);
        if (data != null) {
            return AddressMapper.mapAddressEntityToDTO(data);
        }
        return null;
    }

    @Override
    public boolean setDefaultAddressForCustomer(String customerId, Long addressId) {
        return addressRepository.setDefaultAddressForCustomer(customerId, addressId);
    }
}