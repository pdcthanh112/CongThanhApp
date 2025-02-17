package com.congthanh.cartservice.service;

import com.congthanh.cartservice.model.dto.CartDTO;
import com.congthanh.cartservice.model.request.CreateCartRequest;

import java.util.List;

public interface CartService {

  CartDTO getCartById(Long id);

  List<CartDTO> getActiveCartByCustomerId(String customerId);

  CartDTO createCart(CreateCartRequest request);

  CartDTO updateCart(CartDTO cartDTO);

  boolean deleteCart(Long cartId);

  CartDTO getDefaultCartOfCustomer(String customerId);

  boolean setDefaultCartForCustomer(String customerId, Long cartId);

}
