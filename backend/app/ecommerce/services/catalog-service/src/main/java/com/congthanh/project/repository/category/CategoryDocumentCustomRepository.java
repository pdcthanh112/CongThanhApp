package com.congthanh.project.repository.category;

import com.congthanh.project.model.document.CategoryDocument;
import com.congthanh.project.model.document.SubcategoryDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentCustomRepository {

    void deleteCategory(String categoryId);

    CategoryDocument addSubcategory(SubcategoryDocument subcategory, String categoryId);

    CategoryDocument removeSubcategory(String categoryId, String subcategoryId);

}
