package com.congthanh.project.repository.ecommerce.category;

import com.congthanh.project.entity.ecommerce.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryCustomRepository {

  Optional<Category> findById(int id);

  Optional<Category> findByName(String name);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE mydream.category SET status = 'Deleted' WHERE id = ?1 ")
  boolean deleteCategory(int id);
}
