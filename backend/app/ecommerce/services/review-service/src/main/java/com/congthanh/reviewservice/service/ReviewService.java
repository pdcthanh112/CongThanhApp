package com.congthanh.reviewservice.service;

import com.congthanh.reviewservice.model.dto.ReviewDTO;
import com.congthanh.reviewservice.model.entity.Review;
import com.congthanh.reviewservice.model.request.ReviewFilter;
import com.congthanh.reviewservice.model.response.ResponseWithPagination;
import com.congthanh.reviewservice.model.response.StatisticReviewResponse;
import com.congthanh.reviewservice.model.viewmodel.ReviewVm;

public interface ReviewService {

    ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit);
    ResponseWithPagination<ReviewVm> getReviewVmByProductId(String productId, ReviewFilter filter);

    Review createReview(ReviewDTO reviewDTO);

    StatisticReviewResponse getReviewStatisticOfProduct(String productId);

}
