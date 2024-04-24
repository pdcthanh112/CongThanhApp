package com.congthanh.project.repository.ecommerce.cartItem;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.entity.ecommerce.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, String>, CartItemCustomRepository {

  @Query(nativeQuery = true, value = "SELECT cart_item.id, quantity, cart, product, cart_item.created_date FROM cart_item JOIN cart ON cart_item.cart = cart.id WHERE cart = ?1 AND product = ?2 AND cart.status = '" + StateStatus.STATUS_ACTIVE + "'")
  CartItem checkExistProductFromCart(String cartId, String productId);

}
