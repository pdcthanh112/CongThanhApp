package com.congthanh.catalogservice.repository.tag;

import org.springframework.stereotype.Repository;

@Repository
public interface TagDocumentCustomRepository {

    void deleteTagDocument(Long tagId);

}
