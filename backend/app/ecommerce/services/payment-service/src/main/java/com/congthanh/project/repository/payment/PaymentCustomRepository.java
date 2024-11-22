package com.congthanh.project.repository.payment;

import com.congthanh.project.model.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentCustomRepository {

    Payment createPayment(Payment payment);
}
