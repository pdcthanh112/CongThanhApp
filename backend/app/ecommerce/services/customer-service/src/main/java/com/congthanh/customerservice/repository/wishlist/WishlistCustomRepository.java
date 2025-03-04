package com.congthanh.customerservice.repository.wishlist;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishlistCustomRepository {

//    @Modifying
//    boolean addProductToWishlist(String customerId, String productId);
//
//    @Modifying
//    @Transactional
//    boolean removeProductFromWishlist(String customerId, String productId);
}
