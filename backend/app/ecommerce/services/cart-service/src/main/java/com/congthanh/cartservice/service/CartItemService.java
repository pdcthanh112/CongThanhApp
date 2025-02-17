package com.congthanh.cartservice.service;

import com.congthanh.cartservice.model.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {

  List<CartItemDTO> getItemByCartId(Long cartId);

  CartItemDTO addToCart(String productId, int quantity, Long cartId);

  CartItemDTO updateCartItem(Long cartItemId, int quantity);

  boolean deleteCartItem(Long cartItemId);

}
