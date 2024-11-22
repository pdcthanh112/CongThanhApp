package com.congthanh.project.repository.checkout;

import com.congthanh.project.model.entity.Checkout;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CheckoutRepository extends JpaRepository<Checkout, Integer>, CheckoutCustomRepository {

}
