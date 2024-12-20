package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartCustomRepository {

    List<Cart> findActiveCartByCustomerId(String customerId);

    Cart getDefaultCartOfCustomer(String customerId);

    @Modifying
    boolean setDefaultCartForCustomer(String customerId, String cartId);
}
