package com.congthanh.project.repository;

import com.congthanh.project.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentCustomRepository {

    Payment createPayment(Payment payment);
}
