package com.congthanh.catalogservice.service;

import com.congthanh.catalogservice.model.dto.TagDTO;
import com.congthanh.catalogservice.model.request.CreateTagRequest;
import com.congthanh.catalogservice.model.request.UpdateTagRequest;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;

public interface TagService {

    ResponseWithPagination<TagDTO> getAllTags();

    TagDTO getTagById(Long id);

    TagDTO createTag(CreateTagRequest request);

    TagDTO updateTag(UpdateTagRequest request, Long tagId);

    void deleteTag(Long tagId);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);

}