package com.congthanh.cartservice.repository.cartItem;

import com.congthanh.cartservice.model.entity.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class CartItemCustomRepositoryImpl implements CartItemCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<CartItem> getAllCartItemByCartId(Long cartId) {
        String sql = "SELECT c FROM CartItem c WHERE c.cart.id = :cartId ORDER BY c.createdAt desc ";
        TypedQuery<CartItem> query = entityManager.createQuery(sql, CartItem.class);
        query.setParameter("cartId", cartId);
        return query.getResultList();
    }
}
