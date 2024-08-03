package com.congthanh.project.repository.review;

import com.congthanh.project.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, String>, ReviewCustomRepository {
    @EntityGraph(attributePaths = {"reviewMedia"})
    @Query("SELECT r FROM Review r LEFT JOIN ReviewMedia rm ON r.id = rm.review.id   WHERE r.product.id = :productId ORDER BY r.createdAt DESC")
    Page<Review> getReviewsByProductId(@Param("productId") String productId, Pageable pageable);

}
