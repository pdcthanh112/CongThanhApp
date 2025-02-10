package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.document.ReviewDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDocumentCustomRepositoryImpl implements ReviewDocumentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ReviewDocument> findReviewsWithCriteria(String productId, Integer rating, Boolean hasMedia, Pageable pageable) {
        final Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.where("product").is(productId));

        // Add rating filter if provided
        if (rating != null && rating >= 1 && rating <= 5) {
            criteria.add(Criteria.where("rating").is(rating));
        }

        // Add media filter if provided
        if (hasMedia != null && hasMedia) {
            criteria.add(Criteria.where("reviewMedia").exists(true).ne(null).not().size(0));
        }
//        else {
//            criteria.add(new Criteria().orOperator(
//                    Criteria.where("reviewMedia").exists(false),
//                    Criteria.where("reviewMedia").size(0)
//            ));
//
//        }

        // Combine all criteria
        query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));

        // Get total count for pagination
        long total = mongoTemplate.count(query, ReviewDocument.class);

        // Add pagination and sorting
        query.with(pageable.getSort().descending());
        query.skip(pageable.getOffset());
        query.limit(pageable.getPageSize());

        List<ReviewDocument> reviews = mongoTemplate.find(query, ReviewDocument.class);

        return new PageImpl<>(reviews, pageable, total);
    }

}
