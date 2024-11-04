package com.congthanh.project.repository.tag;

import com.congthanh.project.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public class TagCustomRepositoryImpl implements TagCustomRepository{

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Tag checkExistsTagInProduct(Long tagId, String productId) {
        String sql = "SELECT t FROM product_tag t WHERE tag_id = ?1 AND product_id = ?2";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, tagId);
        query.setParameter(2, productId);

        return (Tag) query.getSingleResult();
    }

    @Override
    public boolean addTagFromProduct(Long tagId, String productId) {
        String sql = "INSERT INTO product_tag (tag_id, product_id) VALUES (?1, ?2) ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, tagId);
        query.setParameter(2, productId);

        return query.executeUpdate() > 0;
    }

    @Override
    public boolean removeTagFromProduct(Long tagId, String productId) {
        String sql = "DELETE FROM product_tag WHERE tag_id = ?1 AND product_id = ?2";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, tagId);
        query.setParameter(2, productId);

        return query.executeUpdate() > 0;
    }
}
