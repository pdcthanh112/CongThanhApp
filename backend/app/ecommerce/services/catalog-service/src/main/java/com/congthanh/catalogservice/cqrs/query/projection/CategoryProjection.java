package com.congthanh.catalogservice.cqrs.query.projection;

import com.congthanh.catalogservice.cqrs.query.query.category.GetAllCategoryQuery;
import com.congthanh.catalogservice.cqrs.query.query.category.GetCategoryByIdQuery;
import com.congthanh.catalogservice.exception.ecommerce.NotFoundException;
import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.repository.category.CategoryDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryProjection {

    private final CategoryDocumentRepository categoryDocumentRepository;

    @QueryHandler
    public List<CategoryDocument> findAll(GetAllCategoryQuery query) {
        return categoryDocumentRepository.findAll();
    }

    @QueryHandler
    public CategoryDocument getCategoryById(GetCategoryByIdQuery query) {
        return categoryDocumentRepository.findById(query.getId()).orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
