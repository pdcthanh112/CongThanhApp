package com.congthanh.project.repository.ecommerce.wishlist;

import com.congthanh.project.entity.ecommerce.Wishlist;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>, WishlistCustomRepository {

  @Query(nativeQuery = true, value = "SELECT wishlist.id as wishlistId, customer, product.id as productId, description, product.name, price, quantity, product.status, category.name as category, subcategory.name as subcategory, production, slug\n" +
          "FROM wishlist JOIN wishlist_item ON wishlist.id = wishlist_item.wishlist_id\n" +
          "JOIN product ON wishlist_item.product_id = product.id\n" +
          "JOIN category ON product.category = category.id\n" +
          "JOIN subcategory ON product.subcategory = subcategory.id\n" +
          "WHERE wishlist.customer = ?1")
  List<Tuple> findWishlistByCustomer(String customerId);

  @Query(nativeQuery = true, value = "SELECT id, customer FROM wishlist WHERE customer = ?1")
  Tuple checkExistWishlist(String customerId);

}
