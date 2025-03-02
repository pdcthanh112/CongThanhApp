package com.congthanh.customerservice.repository.shippingAddress;

import com.congthanh.customerservice.model.entity.ShippingAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long>, ShippingAddressCustomRepository {

}
