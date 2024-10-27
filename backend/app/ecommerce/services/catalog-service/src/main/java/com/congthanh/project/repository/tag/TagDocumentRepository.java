package com.congthanh.project.repository.tag;

import com.congthanh.project.model.document.TagDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDocumentRepository extends MongoRepository<TagDocument, Long>, TagDocumentCustomRepository {
}
