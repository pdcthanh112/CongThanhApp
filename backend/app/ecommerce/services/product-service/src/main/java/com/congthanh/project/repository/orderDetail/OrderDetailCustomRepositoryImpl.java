package com.congthanh.project.repository.orderDetail;

import com.congthanh.project.entity.ecommerce.OrderDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class OrderDetailCustomRepositoryImpl implements OrderDetailCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<OrderDetail> findByStatus(String status, Pageable pageable) {
        String sql = status.equals("ALL") ? "SELECT o FROM OrderDetail o" : "SELECT o FROM OrderDetail o WHERE o.status = :status";
        TypedQuery<OrderDetail> query = entityManager.createQuery(sql, OrderDetail.class);
        if(!status.equals("ALL")) {
            query.setParameter("status", status);
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(), pageable, query.getResultList().size());
    }
}
