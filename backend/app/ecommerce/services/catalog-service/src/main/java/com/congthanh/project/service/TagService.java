package com.congthanh.project.service;

import com.congthanh.project.dto.TagDTO;
import com.congthanh.project.model.request.CreateTagRequest;

import java.util.List;

public interface TagService {

    List<TagDTO> getAllTags();

    TagDTO createTag(CreateTagRequest request);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);

}