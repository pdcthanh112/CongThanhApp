package com.congthanh.customerservice.service;

import com.congthanh.customerservice.model.dto.WishlistDTO;

public interface WishlistService {

    boolean addProductToWishlist(String customerId, String productId);

    boolean removeProductFromWishlist(String customerId, String productId);

    WishlistDTO getWishlistByCustomer(String customerId);

}