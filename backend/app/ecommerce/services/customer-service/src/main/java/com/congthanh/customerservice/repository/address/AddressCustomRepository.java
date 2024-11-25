package com.congthanh.customerservice.repository.address;

import com.congthanh.customerservice.model.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AddressCustomRepository {

    List<Address> getAddressByCustomerId(String customerId);

    Address getDefaultAddressOfCustomer(String customerId);

    @Modifying
    boolean setDefaultAddressForCustomer(String customerId, Long addressId);

}
