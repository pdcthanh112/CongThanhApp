package com.congthanh.notificationservice.repository.notification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;

public class NotificationCustomRepositoryImpl implements NotificationCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
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
