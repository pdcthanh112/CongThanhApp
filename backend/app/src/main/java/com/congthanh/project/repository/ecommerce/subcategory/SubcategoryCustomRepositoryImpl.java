package com.congthanh.project.repository.ecommerce.subcategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class SubcategoryCustomRepositoryImpl implements SubcategoryCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;
}
