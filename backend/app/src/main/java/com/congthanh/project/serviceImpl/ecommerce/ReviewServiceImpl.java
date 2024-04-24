package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ReviewDTO;
import com.congthanh.project.model.ecommerce.request.RatingStarRequest;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.Review;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.review.ReviewRepository;
import com.congthanh.project.service.ecommerce.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewDTO reviewDTO) {
        Product product = productRepository.findById(reviewDTO.getProduct()).orElseThrow(() -> new NotFoundException("product not found"));
        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .product(product)
                .customerId(reviewDTO.getCustomerId())
                .createdAt(new Date().getTime())
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public RatingStarRequest getRatingStarOfProduct(String productId) {
        return reviewRepository.getReviewStatsFromProduct(productId);
    }
}
