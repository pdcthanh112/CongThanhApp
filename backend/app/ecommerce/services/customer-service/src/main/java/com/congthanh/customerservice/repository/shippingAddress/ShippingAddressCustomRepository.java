package com.congthanh.customerservice.repository.shippingAddress;

import com.congthanh.customerservice.model.entity.ShippingAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ShippingAddressCustomRepository {

    List<ShippingAddress> getAddressByCustomerId(String customerId);

    ShippingAddress getDefaultAddressOfCustomer(String customerId);

    @Modifying
    boolean setDefaultAddressForCustomer(String customerId, Long addressId);

}
