package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ReviewDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.entity.Review;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.model.ecommerce.response.StatisticReviewResponse;
import com.congthanh.project.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/review")
@Tag(name = "Review API", description = "Review API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Response<Review>> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.createReview(reviewDTO);
        Response<Review> response = new Response<>();
        response.setData(review);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Response<ResponseWithPagination<ReviewDTO>>> getReviewByProductId(@PathVariable("productId") String productId,  @RequestParam(defaultValue = "All") String filterRequest, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit) {
        ResponseWithPagination<ReviewDTO> data = reviewService.getReviewByProductId(productId, page, limit);
        Response<ResponseWithPagination<ReviewDTO>> response = new Response<>();
        response.setData(data);
        response.setMessage("Get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/statistic")
    public ResponseEntity<Response<StatisticReviewResponse>> getRatingStarOfProduct(@RequestParam("product") String productId) {
        StatisticReviewResponse data = reviewService.getReviewStatisticOfProduct(productId);
        Response<StatisticReviewResponse> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get rating star successfully");
        return ResponseEntity.ok().body(response);
    }
}
