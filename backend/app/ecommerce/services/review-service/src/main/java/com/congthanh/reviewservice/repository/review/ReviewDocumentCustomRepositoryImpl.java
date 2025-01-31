package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.document.ReviewDocument;
import com.congthanh.reviewservice.model.request.ReviewFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDocumentCustomRepositoryImpl implements ReviewDocumentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ReviewDocument> findReviewsWithCriteria(String productId, ReviewFilter filter) {
        Query query = new Query();

        if (filter.rating() >= 1 && filter.rating() <= 5) {
            query.addCriteria(Criteria.where("rating").is(filter.rating()));
        }

        if (Boolean.TRUE.equals(filter.hasMedia())) {
            query.addCriteria(Criteria.where("reviewMedia").not().size(0));
        } else {
            query.addCriteria(Criteria.where("reviewMedia").size(0));
        }

        long total = mongoTemplate.count(query, ReviewDocument.class);

        query.with(PageRequest.of(filter.page() - 1, filter.limit(), Sort.by("createdAt").descending()));

        List<ReviewDocument> reviews = mongoTemplate.find(query, ReviewDocument.class);

        return new PageImpl<>(reviews, PageRequest.of(filter.page() - 1, filter.limit()), total);
    }
}
