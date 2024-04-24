package com.congthanh.project.repository.ecommerce.productImage;

import com.congthanh.project.entity.ecommerce.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductImageCustomRepositoryImpl implements ProductImageCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductImage> getImageByProduct(String productId) {
        String sql = "SELECT i FROM ProductImage i WHERE i.product = :productId ORDER BY isDefault DESC NULLS LAST";
        TypedQuery<ProductImage> query = entityManager.createQuery(sql, ProductImage.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public ProductImage getDefaultImageByProduct(String productId) {
        try {
            String sql = "SELECT i FROM ProductImage i WHERE i.product = :productId AND isDefault = TRUE";
            TypedQuery<ProductImage> query = entityManager.createQuery(sql, ProductImage.class);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
