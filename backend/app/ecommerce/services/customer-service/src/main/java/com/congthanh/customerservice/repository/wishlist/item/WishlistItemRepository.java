package com.congthanh.customerservice.repository.wishlist.item;

import com.congthanh.customerservice.model.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByWishlistIdOrderByCreatedAtAsc(Long wishlistId);

    @Query(value = "DELETE FROM wishlist_item WHERE wishlist_id = (SELECT id FROM wishlist WHERE customer = ?1) AND product_id = ?2", nativeQuery = true)
    void removeItemFromWishlist(String customerId, String productId);

    boolean existsByWishlistIdAndProductId(Long wishlistId, String productId);
}
