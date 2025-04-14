package com.congthanh.orderservice.repository.orderStatusTracking;

import com.congthanh.orderservice.model.entity.OrderStatusTracking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderStatusTrackingCustomRepositoryImpl implements OrderStatusTrackingCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<OrderStatusTracking> getStatusTrackingByOrderCode(String orderCode) {
        String sql = "SELECT t FROM OrderStatusTracking t LEFT JOIN Order o WHERE o.orderCode = :orderCode";
        TypedQuery<OrderStatusTracking> query = entityManager.createQuery(sql, OrderStatusTracking.class);
        query.setParameter("orderCode", orderCode);
        return query.getResultList();
    }
}
