package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartCustomRepository {

    List<Cart> findActiveCartByCustomerId(String customerId);

    Cart getDefaultCartOfCustomer(String customerId);

    @Modifying
    boolean setDefaultCartForCustomer(String customerId, Long cartId);
}
