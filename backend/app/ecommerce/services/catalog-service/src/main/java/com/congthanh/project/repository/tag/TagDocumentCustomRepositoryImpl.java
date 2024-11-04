package com.congthanh.project.repository.tag;

import com.congthanh.project.constant.enums.TagStatus;
import com.congthanh.project.model.document.TagDocument;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TagDocumentCustomRepositoryImpl implements TagDocumentCustomRepository{

    private MongoTemplate mongoTemplate;

    @Override
    public void deleteTagDocument(Long tagId) {
        UpdateResult result = mongoTemplate.updateMulti(
                Query.query(Criteria.where("_id").is(tagId)),
                Update.update("status", TagStatus.INACTIVE), TagDocument.class
        );
    }
}
