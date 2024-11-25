package com.congthanh.productservice.repository.product;

import com.congthanh.productservice.model.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDocumentRepository extends MongoRepository<ProductDocument, String> {

    Optional<ProductDocument> findByProductId(String productId);

}
