package com.congthanh.project.repository.ecommerce.shipping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class ShippingCustomRepositoryImpl implements ShippingCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;
}
