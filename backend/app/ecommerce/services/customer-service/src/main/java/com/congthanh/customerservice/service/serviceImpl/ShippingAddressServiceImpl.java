package com.congthanh.customerservice.service.serviceImpl;

import com.congthanh.customerservice.cqrs.command.command.shippingAddress.CreateShippingAddressCommand;
import com.congthanh.customerservice.model.dto.ShippingAddressDTO;
import com.congthanh.customerservice.model.entity.ShippingAddress;
import com.congthanh.customerservice.exception.ecommerce.NotFoundException;
import com.congthanh.customerservice.model.mapper.AddressMapper;
import com.congthanh.customerservice.model.request.CreateShippingAddressRequest;
import com.congthanh.customerservice.repository.shippingAddress.ShippingAddressRepository;
import com.congthanh.customerservice.service.ShippingAddressService;
import com.congthanh.customerservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository addressRepository;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    private final SnowflakeIdGenerator snowflakeIdGenerator;


    @Override
    public ShippingAddressDTO getAddressById(Long addressId) {
        ShippingAddress result = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("address not found"));
        return AddressMapper.mapAddressEntityToDTO(result);
    }

    @Override
    public ShippingAddressDTO createAddress(CreateShippingAddressRequest request) {
        CreateShippingAddressCommand shippingAddress = CreateShippingAddressCommand.builder()
                .id(snowflakeIdGenerator.nextId())
                .customer(request.getCustomer())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .label(request.getLabel())
                .country(request.getCountry())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .addressLine3(request.getAddressLine3())
                .street(request.getStreet())
                .postalCode(request.getPostalCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .isDefault(request.isDefault())
                .build();
        var response = commandGateway.sendAndWait(shippingAddress);

        return null;
    }

    @Override
    public ShippingAddressDTO updateAddress(Long addressId, ShippingAddressDTO shippingAddressDTO) {
        ShippingAddress exitsShippingAddress = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("address not found"));
        exitsShippingAddress.setPhone(shippingAddressDTO.getPhone());
        exitsShippingAddress.setCountry(shippingAddressDTO.getCountry());
        exitsShippingAddress.setAddressLine1(shippingAddressDTO.getAddressLine1());
        exitsShippingAddress.setAddressLine2(shippingAddressDTO.getAddressLine2());
        exitsShippingAddress.setAddressLine3(shippingAddressDTO.getAddressLine3());
        exitsShippingAddress.setStreet(shippingAddressDTO.getStreet());
        exitsShippingAddress.setStreet(shippingAddressDTO.getStreet());
        exitsShippingAddress.setPostalCode(shippingAddressDTO.getPostalCode());
        ShippingAddress result = addressRepository.save(exitsShippingAddress);
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
    public List<ShippingAddressDTO> getAddressByCustomer(String customerId) {
        List<ShippingAddress> data = addressRepository.getAddressByCustomerId(customerId);
        if (!data.isEmpty()) {
            List<ShippingAddressDTO> result = new ArrayList<>();
            for (ShippingAddress item : data) {
                ShippingAddressDTO address = AddressMapper.mapAddressEntityToDTO(item);
                result.add(address);
            }
            return result;
        }
        return null;
    }

    @Override
    public ShippingAddressDTO getDefaultAddressOfCustomer(String customerId) {
        ShippingAddress data = addressRepository.getDefaultAddressOfCustomer(customerId);
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