package com.congthanh.project.repository.product;

import com.congthanh.project.model.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductQueryRepository extends MongoRepository<ProductDocument, String> {

    Optional<ProductDocument> findByProductId(String productId);

}
