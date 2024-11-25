package com.congthanh.catalogservice.repository.brand;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BrandCustomRepositoryImpl implements BrandCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
