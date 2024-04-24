package com.congthanh.project.repository.ecommerce.notification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class NotificationCustomRepositoryImpl implements NotificationCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;
}
