package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.document.ReviewDocument;
import com.congthanh.reviewservice.model.request.ReviewFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewDocumentCustomRepository {

    Page<ReviewDocument> findReviewsWithCriteria(String productId, Integer rating, Boolean hasMedia, Pageable pageable);

}
