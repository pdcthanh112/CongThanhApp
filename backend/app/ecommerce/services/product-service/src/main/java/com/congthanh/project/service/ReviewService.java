package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ReviewDTO;
import com.congthanh.project.entity.Review;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;

public interface ReviewService {

    ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit);

    Review createReview(ReviewDTO reviewDTO);

    StatisticReviewResponse getReviewStatisticOfProduct(String productId);

}
