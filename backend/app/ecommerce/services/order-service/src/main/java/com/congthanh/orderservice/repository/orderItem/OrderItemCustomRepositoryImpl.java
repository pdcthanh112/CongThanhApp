package com.congthanh.orderservice.repository.orderItem;

import com.congthanh.orderservice.model.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemCustomRepositoryImpl implements OrderItemCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<OrderItem> findByStatus(String status, Pageable pageable) {
        String sql = status.equals("ALL") ? "SELECT o FROM OrderItem o" : "SELECT o FROM OrderDetail o WHERE o.status = :status";
        TypedQuery<OrderItem> query = entityManager.createQuery(sql, OrderItem.class);
        if(!status.equals("ALL")) {
            query.setParameter("status", status);
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }
}
