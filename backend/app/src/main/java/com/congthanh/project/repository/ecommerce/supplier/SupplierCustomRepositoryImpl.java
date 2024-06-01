package com.congthanh.project.repository.ecommerce.supplier;

import com.congthanh.project.entity.ecommerce.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SupplierCustomRepositoryImpl implements SupplierCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> getProductFromSupplier(String storeId, Pageable pageable) {
        String sql = "SELECT p FROM Product p WHERE p.supplier.id = :storeId";
        TypedQuery<Product> query = entityManager.createQuery(sql, Product.class);
        query.setParameter("storeId", storeId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.supplier.id = :storeId", Long.class);
        countQuery.setParameter("storeId", storeId);
        Long totalProducts = countQuery.getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, totalProducts);
    }

}
