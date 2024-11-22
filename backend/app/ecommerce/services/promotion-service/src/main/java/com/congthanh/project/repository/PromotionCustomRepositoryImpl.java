package com.congthanh.project.repository;

import com.congthanh.project.model.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class PromotionCustomRepositoryImpl implements PromotionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Promotion getPromotionByCode(String code) {
        try {
            String sql = "SELECT p FROM Promotion p WHERE p.code = :code";
            TypedQuery<Promotion> query = entityManager.createQuery(sql, Promotion.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Long countUsedPromotion(String promotionId) {
        String sql = "SELECT ";
        TypedQuery<Promotion> query = entityManager.createQuery(sql, Promotion.class);
        return (long) query.executeUpdate();
    }
}
