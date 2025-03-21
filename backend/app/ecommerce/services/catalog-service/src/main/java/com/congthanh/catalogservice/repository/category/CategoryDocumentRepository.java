package com.congthanh.catalogservice.repository.category;

import com.congthanh.catalogservice.model.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentRepository extends MongoRepository<CategoryDocument, String>, CategoryDocumentCustomRepository {

}
