package com.congthanh.project.repository.ecommerce.subcategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;

public class SubcategoryCustomRepositoryImpl implements SubcategoryCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;
}
