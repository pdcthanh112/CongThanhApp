package com.congthanh.productservice.repository.productVariant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class ProductVariantCustomRepositoryImpl implements ProductVariantCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

}
