package com.congthanh.project.repository;

import com.congthanh.project.entity.Supplier;
import com.congthanh.project.model.response.ProductResponse;
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
    public Supplier getSupplierBySlug(String slug) {
        String sql = "SELECT s FROM Supplier s WHERE s.slug = :slug";
        TypedQuery<Supplier> query = entityManager.createQuery(sql, Supplier.class);
        query.setParameter("slug", slug);
        return query.getSingleResult();
    }

    @Override
    public Page<ProductResponse> getProductFromSupplier(String storeId, Pageable pageable) {
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
