package com.congthanh.project.service;

import com.congthanh.project.model.dto.ReviewDTO;
import com.congthanh.project.model.entity.Review;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.model.response.StatisticReviewResponse;

public interface ReviewService {

    ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit);

    Review createReview(ReviewDTO reviewDTO);

    StatisticReviewResponse getReviewStatisticOfProduct(String productId);

}
