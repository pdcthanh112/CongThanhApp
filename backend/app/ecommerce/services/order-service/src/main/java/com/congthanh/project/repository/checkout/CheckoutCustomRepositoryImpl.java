package com.congthanh.project.repository.checkout;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckoutCustomRepositoryImpl implements CheckoutCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

}
