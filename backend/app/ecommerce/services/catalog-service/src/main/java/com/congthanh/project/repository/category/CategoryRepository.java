package com.congthanh.project.repository.category;

import com.congthanh.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, String>, CategoryCustomRepository {

//  Optional<Category> findById(String id);

  Optional<Category> findByName(String name);

}
