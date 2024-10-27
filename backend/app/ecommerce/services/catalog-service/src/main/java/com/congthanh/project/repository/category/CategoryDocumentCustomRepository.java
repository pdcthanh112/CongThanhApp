package com.congthanh.project.repository.category;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentCustomRepository {

    void deleteCategory(String categoryId);

}
