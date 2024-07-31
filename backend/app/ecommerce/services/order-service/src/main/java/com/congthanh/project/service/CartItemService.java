package com.congthanh.project.service;

import com.congthanh.project.dto.ecommerce.CartItemDTO;
import com.congthanh.project.entity.ecommerce.CartItem;

import java.util.List;

public interface CartItemService {

  List<CartItemDTO> getItemByCartId(String cartId);
  CartItemDTO addToCart(String productId, int quantity, String cartId);

  CartItemDTO updateCartItem(String cartItemId, int quantity);

  boolean deleteCartItem(String cartItemId);

}
