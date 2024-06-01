package com.congthanh.project.repository.ecommerce.productVariant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ProductVariantCustomRepositoryImpl implements ProductVariantCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

}
