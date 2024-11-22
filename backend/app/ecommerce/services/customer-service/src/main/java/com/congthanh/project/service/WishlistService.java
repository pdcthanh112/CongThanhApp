package com.congthanh.project.service;

import com.congthanh.project.model.dto.WishlistDTO;

public interface WishlistService {

    boolean addProductToWishlist(String customerId, String productId);

    boolean removeProductFromWishlist(String customerId, String productId);

    WishlistDTO getWishlistByCustomer(String customerId);

}