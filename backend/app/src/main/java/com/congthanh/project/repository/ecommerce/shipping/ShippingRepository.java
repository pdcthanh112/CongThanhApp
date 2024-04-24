package com.congthanh.project.repository.ecommerce.shipping;

import com.congthanh.project.entity.ecommerce.Shipping;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ShippingRepository extends JpaRepository<Shipping, String> {
}
