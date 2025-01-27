package com.congthanh.productservice.repository.product;

import com.congthanh.productservice.model.document.ProductDocument;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDocumentCustomRepository {

    Optional<ProductDocument> findBySlug(String slug);

}
