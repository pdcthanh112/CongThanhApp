package com.congthanh.project.repository.review;

import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Qualifier;


public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public StatisticReviewResponse getStatisticReviewFromProduct(String productId) {
        String sql = "SELECT " +
                "  COALESCE(CAST(COUNT(r.id) AS INTEGER), 0) AS total_reviews,\n" +
                "  COALESCE(CAST(COUNT(rm.id) AS INTEGER), 0) AS reviews_with_media,\n" +
                "  COALESCE(CAST(SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END) AS INTEGER), 0) AS review_with_rating_5,\n" +
                "  COALESCE(CAST(SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END) AS INTEGER), 0) AS review_with_rating_4,\n" +
                "  COALESCE(CAST(SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END) AS INTEGER), 0) AS review_with_rating_3,\n" +
                "  COALESCE(CAST(SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END) AS INTEGER), 0) AS review_with_rating_2,\n" +
                "  COALESCE(CAST(SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END) AS INTEGER), 0) AS review_with_rating_1,\n" +
                "  COALESCE(CAST(AVG(r.rating) AS FLOAT), 0.0) AS average_rating\n" +
                "FROM Review r\n" +
                "LEFT JOIN ReviewMedia rm ON r.id = rm.review.id\n" +
                "WHERE r.product.id = :productId";
        TypedQuery<Tuple> query = entityManager.createQuery(sql, Tuple.class);
        query.setParameter("productId", productId);
        Tuple result = query.getSingleResult();
        StatisticReviewResponse response = StatisticReviewResponse.builder()
                .totalReview(result.get("total_reviews", Integer.class))
                .averageRating(result.get("average_rating", Float.class))
                .reviewWithMedia(result.get("reviews_with_media", Integer.class))
                .reviewRating5Star(result.get("review_with_rating_5", Integer.class))
                .reviewRating4Star(result.get("review_with_rating_4", Integer.class))
                .reviewRating3Star(result.get("review_with_rating_3", Integer.class))
                .reviewRating2Star(result.get("review_with_rating_2", Integer.class))
                .reviewRating1Star(result.get("review_with_rating_1", Integer.class))
                .build();
        return response;
    }
}
