package com.congthanh.productservice.repository.productAttribute;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class ProductAttributeCustomRepositoryImpl implements ProductAttributeCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;


}
