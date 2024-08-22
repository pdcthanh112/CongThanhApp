package com.congthanh.project.repository.tag;

import com.congthanh.project.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TagCustomRepository {

    Optional<Tag> findByTagName(String tagName);

    boolean existsByTagName(String tagName);

    Tag checkExistsTagInProduct(Long tagId, String productId);

    boolean addTagFromProduct(Long tagId, String productId);

    boolean removeTagFromProduct(Long tagId, String productId);
}
