package com.congthanh.project.repository.paymentTransaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class PaymentTransactionCustomRepositoryImpl implements PaymentTransactionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
