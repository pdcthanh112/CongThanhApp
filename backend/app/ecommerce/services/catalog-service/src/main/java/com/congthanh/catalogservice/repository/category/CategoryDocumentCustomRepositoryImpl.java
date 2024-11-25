package com.congthanh.catalogservice.repository.category;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.model.document.SubcategoryDocument;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public class CategoryDocumentCustomRepositoryImpl implements CategoryDocumentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public CategoryDocument addSubcategory(SubcategoryDocument subcategory, String categoryId) {
        Query query = new Query(Criteria.where("id").is(categoryId));

        Update update = new Update().push("subcategories", subcategory);

        return mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                CategoryDocument.class
        );
    }

    @Override
    public CategoryDocument removeSubcategory(String categoryId, String subcategoryId) {
        Query query = new Query(Criteria.where("id").is(categoryId));
        Update update = new Update().pull("subcategories", Query.query(Criteria.where("id").is(subcategoryId)));

        return mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                CategoryDocument.class
        );
    }

    @Override
    public void deleteCategory(String categoryId) {
        UpdateResult parentResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(categoryId)),
                Update.update("status", CategoryStatus.INACTIVE),
                CategoryDocument.class
        );
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRR" + parentResult);
    }
}
