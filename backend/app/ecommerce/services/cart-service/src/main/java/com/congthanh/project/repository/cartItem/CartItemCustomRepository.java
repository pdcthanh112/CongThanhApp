package com.congthanh.project.repository.cartItem;

import com.congthanh.project.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CartItemCustomRepository {

    List<CartItem> getAllCartItemByCartId(String cartId);

}
