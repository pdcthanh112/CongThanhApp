package com.congthanh.project.repository.ecommerce.review;

import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReviewCustomRepository {

    StatisticReviewResponse getStatisticReviewFromProduct(String productId);

}
