package com.congthanh.project.repository;

import com.congthanh.project.entity.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class PaymentCustomRepositoryImpl implements PaymentCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Payment createPayment(Payment payment) {
        return entityManager.merge(payment);
    }
}
