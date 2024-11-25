package com.congthanh.paymentservice.repository.paymentTransaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class PaymentTransactionCustomRepositoryImpl implements PaymentTransactionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
