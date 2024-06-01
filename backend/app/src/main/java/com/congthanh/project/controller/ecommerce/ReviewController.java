package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ReviewDTO;
import com.congthanh.project.model.ecommerce.request.RatingStarRequest;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.entity.ecommerce.Review;
import com.congthanh.project.service.ecommerce.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/review")
@Tag(name = "Review API", description = "Review API in CongThanhApp - Ecommerce")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @PostMapping("/create")
    public ResponseEntity<Response<Review>> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.createReview(reviewDTO);
        Response<Review> response = new Response<>();
        response.setData(review);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getRatingStar")
    public ResponseEntity<Response<RatingStarRequest>> getRatingStarOfProduct(@RequestParam("product") String productId) {
        RatingStarRequest rating = reviewService.getRatingStarOfProduct(productId);
        Response<RatingStarRequest> response = new Response<>();
        response.setData(rating);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get rating star successfully");
        return ResponseEntity.ok().body(response);
    }
}
