package com.congthanh.catalogservice.repository.category;

import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.model.document.SubcategoryDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentCustomRepository {

    void deleteCategory(String categoryId);

    CategoryDocument addSubcategory(SubcategoryDocument subcategory, String categoryId);

    CategoryDocument removeSubcategory(String categoryId, String subcategoryId);

}
