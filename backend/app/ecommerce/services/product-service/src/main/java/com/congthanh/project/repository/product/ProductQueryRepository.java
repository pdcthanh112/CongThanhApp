package com.congthanh.project.repository.product;

import com.congthanh.project.model.document.ProductQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductQueryRepository extends MongoRepository<ProductQuery, String> {

    Optional<ProductQuery> findByProductId(String productId);

}
