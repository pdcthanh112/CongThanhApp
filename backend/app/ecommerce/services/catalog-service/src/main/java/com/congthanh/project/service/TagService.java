package com.congthanh.project.service;

import com.congthanh.project.model.dto.TagDTO;
import com.congthanh.project.model.request.CreateTagRequest;
import com.congthanh.project.model.request.UpdateTagRequest;
import com.congthanh.project.model.response.ResponseWithPagination;

public interface TagService {

    ResponseWithPagination<TagDTO> getAllTags();

    TagDTO getTagById(Long id);

    TagDTO createTag(CreateTagRequest request);

    TagDTO updateTag(UpdateTagRequest request, Long tagId);

    void deleteTag(Long tagId);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);

}