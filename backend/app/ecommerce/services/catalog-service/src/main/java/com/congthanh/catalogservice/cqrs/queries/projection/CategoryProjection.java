package com.congthanh.catalogservice.cqrs.queries.projection;

import com.congthanh.catalogservice.constant.common.ErrorCode;
import com.congthanh.catalogservice.cqrs.queries.query.category.GetAllCategoryQuery;
import com.congthanh.catalogservice.cqrs.queries.query.category.GetCategoryByIdQuery;
import com.congthanh.commonservice.exception.NotFoundException;
import com.congthanh.catalogservice.model.document.CategoryDocument;
import com.congthanh.catalogservice.repository.category.CategoryDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryProjection {

    private final CategoryDocumentRepository categoryDocumentRepository;

    @QueryHandler
    public Page<CategoryDocument> findAll(GetAllCategoryQuery query) {
        if (query.getPage() == null || query.getSize() == null) {
            Sort sort = createSort(query.getSort(), query.getOrder());
            return new PageImpl<>(categoryDocumentRepository.findAll(sort));
        }
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), createSort(query.getSort(), query.getOrder()));
        return categoryDocumentRepository.findAll(pageable);
    }

    @QueryHandler
    public CategoryDocument getCategoryById(GetCategoryByIdQuery query) {
        return categoryDocumentRepository.findById(query.getId()).orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND, query.getId()));
    }

    private Sort createSort(String sortBy, String order) {
        String field = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "name";

        Sort.Direction direction = Sort.Direction.ASC;
        if (order != null && order.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.DESC;
        }

        return Sort.by(direction, field);
    }
}
