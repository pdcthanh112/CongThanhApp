package com.congthanh.productservice.repository.product;

import com.congthanh.productservice.model.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String>, ProductCustomRepository {

  @Query(nativeQuery = true, value = "SELECT product.*\n" +
          "FROM product JOIN category on product.category = category.id\n" +
          "JOIN subcategory on product.subcategory = subcategory.id\n" +
          "WHERE product.slug ILIKE ?1")
  Optional<Product> findProductBySlug(String slug);

  Optional<Product> findByName(String name);

//  Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
//
//  Page<Product> findBySubcategoryId(Long subcategoryId, Pageable pageable);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE mydream.product SET status = 'Deleted' WHERE id = ?1 ")
  boolean deleteProduct(String id);

}