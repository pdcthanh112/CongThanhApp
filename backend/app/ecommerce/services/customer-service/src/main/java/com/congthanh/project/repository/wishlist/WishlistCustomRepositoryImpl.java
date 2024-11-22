package com.congthanh.project.repository.wishlist;

import com.congthanh.project.model.entity.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;

public class WishlistCustomRepositoryImpl implements WishlistCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public boolean addProductToWishlist(String customerId, String productId) {
        String sql = "INSERT INTO wishlist_item (wishlist_id, product_id) VALUES ((SELECT id FROM wishlist WHERE customer = ?1), ?2)";
        Query query = entityManager.createNativeQuery(sql, Wishlist.class);
        query.setParameter(1, customerId);
        query.setParameter(2, productId);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean removeProductFromWishlist(String customerId, String productId) {
        String sql = "DELETE FROM wishlist_item WHERE wishlist_id = (SELECT id FROM wishlist WHERE customer = ?1) AND product_id = ?2";
        Query query = entityManager.createNativeQuery(sql, Wishlist.class);
        query.setParameter(1, customerId);
        query.setParameter(2, productId);
        return query.executeUpdate() > 0;
    }
}
