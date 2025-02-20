package com.congthanh.cartservice.service;

import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.request.AddItemToCartRequest;
import com.congthanh.cartservice.model.viewmodel.CartItemDetailVm;

import java.util.List;

public interface CartItemService {

  List<CartItemDTO> getItemByCartId(Long cartId);

  CartItemDetailVm getCartItemDetail(Long cartId, Long itemId);

  CartItemDTO addToCart(Long cartId, AddItemToCartRequest request);

  CartItemDTO updateCartItem(Long cartItemId, int quantity);

  boolean deleteCartItem(Long cartItemId);

}
