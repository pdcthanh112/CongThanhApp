package com.congthanh.reviewservice.service;

import com.congthanh.reviewservice.model.dto.ReviewDTO;
import com.congthanh.reviewservice.model.entity.Review;
import com.congthanh.reviewservice.model.response.ResponseWithPagination;
import com.congthanh.reviewservice.model.response.StatisticReviewResponse;

public interface ReviewService {

    ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit);

    Review createReview(ReviewDTO reviewDTO);

    StatisticReviewResponse getReviewStatisticOfProduct(String productId);

}
