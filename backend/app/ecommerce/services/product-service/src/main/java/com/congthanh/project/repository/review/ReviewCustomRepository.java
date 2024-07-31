package com.congthanh.project.repository.review;

import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReviewCustomRepository {

    StatisticReviewResponse getStatisticReviewFromProduct(String productId);

}
