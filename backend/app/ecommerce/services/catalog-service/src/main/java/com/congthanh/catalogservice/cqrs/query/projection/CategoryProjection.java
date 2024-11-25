package com.congthanh.catalogservice.cqrs.query.projection;

import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.repository.category.CategoryDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryProjection {

    private final CategoryDocumentRepository categoryDocumentRepository;

    @QueryHandler
    public CategoryDocument getCategoryById(String id) {
        return categoryDocumentRepository.findById(id).orElse(null);
    }
}
