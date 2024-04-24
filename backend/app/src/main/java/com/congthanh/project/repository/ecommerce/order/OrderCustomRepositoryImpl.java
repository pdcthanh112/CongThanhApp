package com.congthanh.project.repository.ecommerce.order;

import com.congthanh.project.entity.ecommerce.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
}

