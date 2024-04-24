package com.congthanh.project.repository.ecommerce.category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CategoryCustomRepositoryImpl implements CategoryCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;
}
