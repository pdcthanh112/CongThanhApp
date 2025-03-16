package com.congthanh.productservice.repository.productImage;

import com.congthanh.productservice.model.entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ProductImageCustomRepositoryImpl implements ProductImageCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public ProductImage getDefaultImageByProduct(String productId) {
        try {
            String sql = "SELECT i FROM ProductImage i WHERE i.product.id = :productId AND isDefault = TRUE";
            TypedQuery<ProductImage> query = entityManager.createQuery(sql, ProductImage.class);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteByProductId(String productId) {
        try {
            String sql = "DELETE FROM ProductImage i WHERE i.product.id = :productId";
            TypedQuery<ProductImage> query = entityManager.createQuery(sql, ProductImage.class);
            query.setParameter("productId", productId);
        } catch (NoResultException e) {
            throw new RuntimeException("Lỗi");
        }
    }
}
