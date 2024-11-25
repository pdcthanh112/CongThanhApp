package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.response.StatisticReviewResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReviewCustomRepository {

    StatisticReviewResponse getStatisticReviewFromProduct(String productId);

}
