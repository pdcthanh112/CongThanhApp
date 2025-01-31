package com.congthanh.reviewservice.service.serviceImpl;

import com.congthanh.reviewservice.model.dto.ReviewDTO;
import com.congthanh.reviewservice.model.mapper.ReviewMapper;
import com.congthanh.reviewservice.model.entity.Review;
import com.congthanh.reviewservice.model.request.ReviewFilter;
import com.congthanh.reviewservice.model.response.*;
import com.congthanh.reviewservice.model.viewmodel.ReviewMediaVm;
import com.congthanh.reviewservice.model.viewmodel.ReviewVm;
import com.congthanh.reviewservice.repository.review.ReviewDocumentRepository;
import com.congthanh.reviewservice.repository.review.ReviewRepository;
import com.congthanh.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDocumentRepository reviewDocumentRepository;

    @Override
    public ResponseWithPagination<ReviewDTO> getReviewByProductId(String productId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Review> result = reviewRepository.getReviewsByProductId(productId, pageable);
        if (result.hasContent()) {
            ResponseWithPagination<ReviewDTO> response = new ResponseWithPagination<>();
            List<ReviewDTO> list = new ArrayList<>();
            for (Review review : result.getContent()) {
                ReviewDTO reviewDTO = ReviewMapper.mapReviewEntityToDTO(review);
                list.add(reviewDTO);
            }
            PaginationInfo paginationInfo = PaginationInfo.builder()
                    .page(page)
                    .limit(limit)
                    .totalPage(result.getTotalPages())
                    .totalElement(result.getTotalElements())
                    .build();
            response.setResponseList(list);
            response.setPaginationInfo(paginationInfo);
            return response;
        } else {
            throw new RuntimeException("List empty exception");
        }
    }

    @Override
    public ResponseWithPagination<ReviewVm> getReviewVmByProductId(String productId, ReviewFilter filter) {
        Pageable pageable = PageRequest.of(filter.page() - 1, filter.limit());
        Page<Review> result = reviewDocumentRepository.findReviewsWithCriteria(productId, pageable);
        if (result.hasContent()) {
            ResponseWithPagination<ReviewVm> response = new ResponseWithPagination<>();
            List<ReviewVm> list = new ArrayList<>();
            for (Review review : result.getContent()) {
                ReviewVm reviewVm = ReviewVm.builder()
                        .id(review.getId())
                        .author(review.getAuthor())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .product(review.getProduct())
                        .variant(review.getVariant())
                        .reviewMedia(review.getReviewMedia().stream().map(item -> ReviewMediaVm.builder().id(item.getId()).url(item.getUrl()).build()).toList())
                        .createdAt(review.getCreatedAt())
                        .build();
                list.add(reviewVm);
            }
            PaginationInfo paginationInfo = PaginationInfo.builder()
                    .page(filter.page())
                    .limit(filter.limit())
                    .totalPage(result.getTotalPages())
                    .totalElement(result.getTotalElements())
                    .build();
            response.setResponseList(list);
            response.setPaginationInfo(paginationInfo);
            return response;
        } else {
            throw new RuntimeException("List empty exception");
        }
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) {
        ProductResponse product = null;
        ProductVariantResponse variant = null;
        assert product != null && variant != null;
        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .product(product.getId())
                .variant(variant.getId())
                .customerId(reviewDTO.getCustomerId())
                .createdAt(Instant.now())
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public StatisticReviewResponse getReviewStatisticOfProduct(String productId) {
        ProductResponse product = null;
        StatisticReviewResponse result = reviewRepository.getStatisticReviewFromProduct(product.getId());
        return result;
    }

}
