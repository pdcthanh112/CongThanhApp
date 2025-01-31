package com.congthanh.reviewservice.repository.review;

import com.congthanh.reviewservice.model.document.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDocumentRepository extends MongoRepository<ReviewDocument, Long>, ReviewDocumentCustomRepository {
}
