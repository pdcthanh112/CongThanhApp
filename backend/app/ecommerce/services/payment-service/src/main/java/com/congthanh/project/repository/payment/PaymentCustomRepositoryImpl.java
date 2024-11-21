package com.congthanh.project.repository.payment;

import com.congthanh.project.entity.Payment;
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
