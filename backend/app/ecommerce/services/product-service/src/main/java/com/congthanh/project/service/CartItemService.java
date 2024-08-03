package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {

  List<CartItemDTO> getItemByCartId(String cartId);
  CartItemDTO addToCart(String productId, int quantity, String cartId);

  CartItemDTO updateCartItem(String cartItemId, int quantity);

  boolean deleteCartItem(String cartItemId);

}
