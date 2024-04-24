package com.congthanh.project.repository.ecommerce.productAttribute;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ProductAttributeCustomRepositoryImpl implements ProductAttributeCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;
}
