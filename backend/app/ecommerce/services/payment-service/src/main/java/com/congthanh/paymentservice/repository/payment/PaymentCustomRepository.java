package com.congthanh.paymentservice.repository.payment;

import com.congthanh.paymentservice.model.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentCustomRepository {

    Payment createPayment(Payment payment);
}
