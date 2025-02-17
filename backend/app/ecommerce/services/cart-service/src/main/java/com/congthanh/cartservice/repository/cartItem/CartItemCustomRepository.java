package com.congthanh.cartservice.repository.cartItem;

import com.congthanh.cartservice.model.entity.CartItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartItemCustomRepository {

    List<CartItem> getAllCartItemByCartId(Long cartId);

}
