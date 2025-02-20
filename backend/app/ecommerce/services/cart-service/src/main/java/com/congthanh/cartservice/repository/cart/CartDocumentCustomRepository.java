package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.document.CartItemDocument;

import java.util.Optional;

public interface CartDocumentCustomRepository {

    void setDefaultCartForCustomer(String customerId, Long cartId);

    void addItemToCart(Long cartId, CartItemDocument document);

    Optional<CartItemDocument> getCartItemDetail(Long cartId, Long temId);
}
