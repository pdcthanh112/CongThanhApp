package com.congthanh.project.repository.ecommerce.product;

import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.enums.ecommerce.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> searchProduct(String keyword) {
        String sql = "SELECT product.* FROM Product JOIN category on product.category = category.id JOIN subcategory on product.subcategory = subcategory.id WHERE CONCAT(product.name, category.name, subcategory.name) ILIKE :keyword";
        TypedQuery<Product> query = entityManager.createQuery(sql, Product.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    @Override
    public boolean checkExistSlug(String slug) {
        String sql = "SELECT id FROM Product WHERE slug = :slug";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("slug", slug);
        List<Long> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public Long countTotalSoldProduct(String productId) {
        String sql = "SELECT SUM(quantity) FROM OrderDetail WHERE product.id = :productId AND status = :status";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("productId", productId);
        query.setParameter("status", OrderStatus.COMPLETED.name());
        return query.getSingleResult();
    }

}
