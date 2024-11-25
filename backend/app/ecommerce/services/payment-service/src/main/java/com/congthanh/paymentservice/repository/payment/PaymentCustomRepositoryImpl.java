package com.congthanh.paymentservice.repository.payment;

import com.congthanh.paymentservice.model.entity.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class PaymentCustomRepositoryImpl implements PaymentCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment createPayment(Payment payment) {
        return entityManager.merge(payment);
    }
}
