package com.congthanh.orderservice.repository.order;

import com.congthanh.orderservice.model.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Order> getOrderHistoryByCustomer(String customerId) {
        String sql = "SELECT o FROM Order o WHERE o.customer = :customerId ORDER BY o.orderDate ASC";
        TypedQuery<Order> query = entityManager.createQuery(sql, Order.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}

