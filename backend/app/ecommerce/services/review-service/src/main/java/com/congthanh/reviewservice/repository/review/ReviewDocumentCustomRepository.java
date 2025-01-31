package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.document.ReviewDocument;
import com.congthanh.reviewservice.model.request.ReviewFilter;
import org.springframework.data.domain.Page;

public interface ReviewDocumentCustomRepository {

    Page<ReviewDocument> findReviewsWithCriteria(String productId, ReviewFilter filter);

}
