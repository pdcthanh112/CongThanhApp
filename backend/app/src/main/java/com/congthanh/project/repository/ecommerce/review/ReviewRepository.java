package com.congthanh.project.repository.ecommerce.review;

import com.congthanh.project.entity.ecommerce.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, String>, ReviewCustomRepository {
}
