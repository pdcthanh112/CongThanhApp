package com.congthanh.shippingservice.repository;

import com.congthanh.shippingservice.model.entity.Shipping;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ShippingRepository extends JpaRepository<Shipping, String> {
}
