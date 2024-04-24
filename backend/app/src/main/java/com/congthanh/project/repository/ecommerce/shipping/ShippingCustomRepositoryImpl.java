package com.congthanh.project.repository.ecommerce.shipping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ShippingCustomRepositoryImpl implements ShippingCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
