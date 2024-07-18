package com.congthanh.project.repository.ecommerce.tag;

import com.congthanh.project.entity.ecommerce.Tag;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

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
