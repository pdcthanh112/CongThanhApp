package com.congthanh.project.repository.tag;

import com.congthanh.project.entity.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {
}
