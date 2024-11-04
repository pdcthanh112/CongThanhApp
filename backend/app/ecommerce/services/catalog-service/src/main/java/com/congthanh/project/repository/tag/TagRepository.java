package com.congthanh.project.repository.tag;

import com.congthanh.project.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

    Optional<Tag> findByName(String name);

}
