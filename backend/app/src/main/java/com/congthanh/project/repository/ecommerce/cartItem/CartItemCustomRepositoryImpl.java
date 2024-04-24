package com.congthanh.project.repository.ecommerce.cartItem;

import com.congthanh.project.entity.ecommerce.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CartItemCustomRepositoryImpl implements CartItemCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartItem> getAllCartItemByCartId(String cartId) {
        String sql = "SELECT c FROM CartItem c WHERE c.cart.id = :cartId ORDER BY c.createdAt desc ";
        TypedQuery<CartItem> query = entityManager.createQuery(sql, CartItem.class);
        query.setParameter("cartId", cartId);
        return query.getResultList();
    }
}
