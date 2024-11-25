package com.congthanh.catalogservice.cqrs.query.projection;

import com.congthanh.catalogservice.cqrs.query.query.tag.GetAllTagQuery;
import com.congthanh.catalogservice.cqrs.query.query.tag.GetTagByIdQuery;
import com.congthanh.catalogservice.model.document.TagDocument;
import com.congthanh.catalogservice.model.response.PaginationInfo;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.catalogservice.repository.tag.TagDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagProjection {

    private final TagDocumentRepository tagDocumentRepository;

    @QueryHandler
    public ResponseWithPagination<TagDocument> query(GetAllTagQuery query) {
        Sort sort = Sort.by(
                query.getSortDirection() != null && query.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                StringUtils.hasText(query.getSortBy()) ? query.getSortBy() : "id"
        );

        if (query.getPage() != null && query.getLimit() != null) {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit(), sort);
            Page<TagDocument> tagPage = tagDocumentRepository.findAll(pageable);

            return ResponseWithPagination.<TagDocument>builder()
                    .responseList(tagPage.getContent())
                    .paginationInfo(PaginationInfo.builder()
                            .page(tagPage.getNumber())
                            .limit(tagPage.getSize())
                            .totalPage(tagPage.getTotalPages())
                            .totalElement(tagPage.getTotalElements())
                            .build())

                    .build();
        } else {
            List<TagDocument> allTags = tagDocumentRepository.findAll(sort);

            return ResponseWithPagination.<TagDocument>builder()
                    .responseList(allTags)
                    .build();
        }

    }

    @QueryHandler
    public TagDocument getTagById(GetTagByIdQuery query) {
        return tagDocumentRepository.findById(query.getId()).orElse(null);
    }

}
