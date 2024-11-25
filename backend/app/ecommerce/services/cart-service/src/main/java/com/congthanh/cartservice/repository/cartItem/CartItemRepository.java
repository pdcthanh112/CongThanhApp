package com.congthanh.cartservice.repository.cartItem;

import com.congthanh.cartservice.constant.common.StateStatus;
import com.congthanh.cartservice.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, String>, CartItemCustomRepository {

  @Query(nativeQuery = true, value = "SELECT cart_item.id, quantity, cart, product, cart_item.created_date FROM cart_item JOIN cart ON cart_item.cart = cart.id WHERE cart = ?1 AND product = ?2 AND cart.status = '" + StateStatus.STATUS_ACTIVE + "'")
  CartItem checkExistProductFromCart(String cartId, String productId);

}
