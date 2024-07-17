package com.congthanh.project.repository.ecommerce.productImage;

import com.congthanh.project.entity.ecommerce.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

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
}
