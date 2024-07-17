package com.congthanh.project.repository.ecommerce.payment;

import com.congthanh.project.entity.ecommerce.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentCustomRepository {

    Payment createPayment(Payment payment);
}
