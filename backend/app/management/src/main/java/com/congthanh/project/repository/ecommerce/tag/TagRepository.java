package com.congthanh.project.repository.ecommerce.tag;

import com.congthanh.project.entity.ecommerce.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {
}
