package com.congthanh.project.repository.ecommerce.review;

import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import jakarta.persistence.*;


public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StatisticReviewResponse getStatisticReviewFromProduct(String productId) {
        String sql = "SELECT CAST (COUNT(r.id) AS INTEGER) AS total_reviews, " +
                "CAST(COUNT(rm.id) AS INTEGER) AS reviews_with_media, " +
                "CAST(SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END) AS INTEGER) AS review_with_rating_5," +
                "CAST( SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END) AS INTEGER) AS review_with_rating_4," +
                "CAST( SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END) AS INTEGER) AS review_with_rating_3, " +
                "CAST (SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END) AS INTEGER) AS review_with_rating_2, " +
                "CAST (SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END) AS INTEGER )AS review_with_rating_1, " +
                "CAST (AVG(r.rating) AS FLOAT) AS average_rating " +
                "FROM Review r LEFT JOIN ReviewMedia rm ON r.id = rm.review.id " +
                "WHERE r.product.id = :productId";
        TypedQuery<Tuple> query = entityManager.createQuery(sql, Tuple.class);
        query.setParameter("productId", productId);
        Tuple result = query.getSingleResult();
        StatisticReviewResponse response = StatisticReviewResponse.builder()
                .totalReview(result.get("total_reviews", Integer.class))
                .averageRating(result.get("average_rating", Float.class))
                .reviewWithMedia(result.get("reviews_with_media", Integer.class))
                .reviewWithRating5(result.get("review_with_rating_5", Integer.class))
                .reviewWithRating4(result.get("review_with_rating_4", Integer.class))
                .reviewWithRating3(result.get("review_with_rating_3", Integer.class))
                .reviewWithRating2(result.get("review_with_rating_2", Integer.class))
                .reviewWithRating1(result.get("review_with_rating_1", Integer.class))
                .build();
        return response;
    }
}
