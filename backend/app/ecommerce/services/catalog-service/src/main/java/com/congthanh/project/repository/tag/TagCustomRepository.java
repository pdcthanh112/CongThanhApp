package com.congthanh.project.repository.tag;

import com.congthanh.project.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TagCustomRepository {

    Tag checkExistsTagInProduct(Long tagId, String productId);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);
}
