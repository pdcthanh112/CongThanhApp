package com.congthanh.project.service.ecommerce;


import com.congthanh.project.dto.ecommerce.ReviewDTO;
import com.congthanh.project.model.ecommerce.request.RatingStarRequest;
import com.congthanh.project.entity.ecommerce.Review;

public interface ReviewService {

    Review createReview(ReviewDTO reviewDTO);

    RatingStarRequest getRatingStarOfProduct(String productId);

}
