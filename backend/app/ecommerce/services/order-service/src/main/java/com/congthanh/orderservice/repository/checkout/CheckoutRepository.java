package com.congthanh.orderservice.repository.checkout;

import com.congthanh.orderservice.model.entity.Checkout;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CheckoutRepository extends JpaRepository<Checkout, Integer>, CheckoutCustomRepository {

}
