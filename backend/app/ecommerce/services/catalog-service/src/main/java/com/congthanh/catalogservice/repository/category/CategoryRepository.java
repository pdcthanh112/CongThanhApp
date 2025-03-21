package com.congthanh.catalogservice.repository.category;

import com.congthanh.catalogservice.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, String>, CategoryCustomRepository {

    Optional<Category> findByName(String name);

    Collection<Category> findByIdIn(List<String> ids);
}
