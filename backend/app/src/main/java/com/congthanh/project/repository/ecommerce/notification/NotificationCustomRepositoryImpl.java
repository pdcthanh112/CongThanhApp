package com.congthanh.project.repository.ecommerce.notification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class NotificationCustomRepositoryImpl implements NotificationCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean changeNotificationReadStatus(Long notificationId, boolean status) {
        String sql  = "UPDATE Notification n SET n.isRead = :status WHERE n.id = :notificationId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("status", status);
        query.setParameter("notificationId", notificationId);
        return query.executeUpdate() > 0;
    }
}
