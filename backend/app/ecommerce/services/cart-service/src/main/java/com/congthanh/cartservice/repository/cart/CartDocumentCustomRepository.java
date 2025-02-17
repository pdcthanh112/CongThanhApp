package com.congthanh.cartservice.repository.cart;

public interface CartDocumentCustomRepository {

    void setDefaultCartForCustomer(String customerId, Long cartId);
}
