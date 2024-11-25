package com.congthanh.cartservice.service;

import com.congthanh.cartservice.model.dto.CartDTO;

import java.util.List;

public interface CartService {

  CartDTO getCartById(String id);

  List<CartDTO> getActiveCartByCustomerId(String customerId);

  CartDTO createCart(CartDTO cartDTO);

  CartDTO updateCart(CartDTO cartDTO);

  boolean deleteCart(String cartId);

  CartDTO getDefaultCartOfCustomer(String customerId);

  boolean setDefaultCartForCustomer(String customerId, String cartId);

}
