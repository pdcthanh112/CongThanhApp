package com.congthanh.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentCustomRepository {

    Payment createPayment(Payment payment);
}
