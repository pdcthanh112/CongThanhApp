package com.congthanh.customerservice.repository.wishlist;

import com.congthanh.customerservice.model.entity.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>, WishlistCustomRepository {

  @Query(nativeQuery = true, value = "SELECT id, customer_id FROM wishlist WHERE customer_id = ?1")
  Wishlist getWishlistByCustomer(String customerId);

}
