package com.congthanh.project.repository.category;

import com.congthanh.project.model.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryQueryRepository extends MongoRepository<CategoryDocument, String> {


}
