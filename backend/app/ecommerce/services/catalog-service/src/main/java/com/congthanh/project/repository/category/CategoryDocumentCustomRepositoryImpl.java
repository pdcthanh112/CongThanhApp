package com.congthanh.project.repository.category;

import com.congthanh.project.constant.enums.CategoryStatus;
import com.congthanh.project.model.document.CategoryDocument;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CategoryDocumentCustomRepositoryImpl implements CategoryDocumentCustomRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void deleteCategory(String categoryId) {
        UpdateResult parentResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(categoryId)),
                Update.update("status", CategoryStatus.INACTIVE),
                CategoryDocument.class
        );
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRR"+parentResult);
    }
}
