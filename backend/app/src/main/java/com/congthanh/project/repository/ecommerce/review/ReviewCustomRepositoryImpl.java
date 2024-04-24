package com.congthanh.project.repository.ecommerce.review;

import com.congthanh.project.model.ecommerce.request.RatingStarRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


public class ReviewCustomRepositoryImpl implements ReviewCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RatingStarRequest getReviewStatsFromProduct(String productId) {
        String sql = "SELECT COUNT(rating), AVG(rating) FROM Review  WHERE product.id = :productId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("productId", productId);
        Object[] result = (Object[]) query.getSingleResult();

        Long vote = result[0] != null ? (Long) result[0] : 0;
        Double value = result[1] != null ? (Double) result[1] : 0;

        return new RatingStarRequest(vote, value);
    }
}
