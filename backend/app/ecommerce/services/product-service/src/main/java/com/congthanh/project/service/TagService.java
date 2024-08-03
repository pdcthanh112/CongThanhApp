package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> getAllTags();

    TagDTO createTag(TagDTO tag);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);

}
