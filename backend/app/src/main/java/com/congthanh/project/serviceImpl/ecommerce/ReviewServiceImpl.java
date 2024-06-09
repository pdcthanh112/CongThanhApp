package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ReviewDTO;
import com.congthanh.project.entity.ecommerce.ProductVariant;
import com.congthanh.project.model.ecommerce.mapper.ReviewMapper;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.Review;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.productVariant.ProductVariantRepository;
import com.congthanh.project.repository.ecommerce.review.ReviewRepository;
import com.congthanh.project.service.ecommerce.ReviewService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Review> data = reviewRepository.getReviewsByProductId(productId, pageable);
        if (data.hasContent()) {
            ResponseWithPagination<ReviewDTO> response = new ResponseWithPagination<>();
            List<ReviewDTO> list = new ArrayList<>();
            for (Review review : data) {
                ReviewDTO reviewDTO = ReviewMapper.mapReviewEntityToDTO(review);
                list.add(reviewDTO);
            }
            response.setResponseList(list);
            response.setTotalPage(data.getTotalPages());
            return response;
        } else {
            throw new RuntimeException("List empty exception");
        }
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) {
        Product product = productRepository.findById(reviewDTO.getProduct()).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductVariant variant = productVariantRepository.findById(reviewDTO.getVariant()).orElseThrow(() -> new NotFoundException("Variant not found"));
        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .product(product)
                .variant(variant)
                .customerId(reviewDTO.getCustomerId())
                .createdAt(new Date().getTime())
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public StatisticReviewResponse getReviewStatisticOfProduct(String productId) {
        StatisticReviewResponse result = reviewRepository.getStatisticReviewFromProduct(productId);
        return result;
    }

}
