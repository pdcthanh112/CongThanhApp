package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.WishlistDTO;

public interface WishlistService {

  boolean addProductToWishlist(String customerId, String productId);

  boolean removeProductFromWishlist(String customerId, String productId);

  WishlistDTO getWishlistByCustomer(String customerId);

}
