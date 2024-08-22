package com.congthanh.project.repository.cartItem;

import com.congthanh.project.entity.CartItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartItemCustomRepository {

    List<CartItem> getAllCartItemByCartId(String cartId);

}
