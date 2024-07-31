package com.congthanh.project.repository.wishlist;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface WishlistCustomRepository {

    @Modifying
    boolean addProductToWishlist(String customerId, String productId);

    @Modifying
    boolean removeProductFromWishlist(String customerId, String productId);
}
