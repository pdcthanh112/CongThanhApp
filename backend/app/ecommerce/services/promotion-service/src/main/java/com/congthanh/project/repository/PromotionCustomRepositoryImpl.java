package com.congthanh.project.repository;

import com.congthanh.project.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

public class PromotionCustomRepositoryImpl implements PromotionCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Promotion getVoucherByCode(String code) {
        try {
            String sql = "SELECT v FROM Promotion v WHERE v.code = :code";
            TypedQuery<Promotion> query = entityManager.createQuery(sql, Promotion.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Long countUsedVoucher(String voucherId) {
        String sql = "SELECT ";
        TypedQuery<Promotion> query = entityManager.createQuery(sql, Promotion.class);
        return (long) query.executeUpdate();
    }
}
