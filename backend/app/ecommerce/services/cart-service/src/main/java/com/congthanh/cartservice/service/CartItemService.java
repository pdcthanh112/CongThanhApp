package com.congthanh.cartservice.service;

import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.request.AddItemToCartRequest;

import java.util.List;

public interface CartItemService {

  List<CartItemDTO> getItemByCartId(Long cartId);

  CartItemDTO addToCart(Long cartId, AddItemToCartRequest request);

  CartItemDTO updateCartItem(Long cartItemId, int quantity);

  boolean deleteCartItem(Long cartItemId);

}
