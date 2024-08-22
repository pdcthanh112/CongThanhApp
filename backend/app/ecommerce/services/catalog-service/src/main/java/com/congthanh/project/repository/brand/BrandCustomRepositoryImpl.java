package com.congthanh.project.repository.brand;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BrandCustomRepositoryImpl implements BrandCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
