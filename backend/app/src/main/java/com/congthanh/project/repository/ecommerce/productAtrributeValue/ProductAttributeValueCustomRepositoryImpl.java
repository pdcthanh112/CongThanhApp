package com.congthanh.project.repository.ecommerce.productAtrributeValue;

import com.congthanh.project.entity.ecommerce.ProductAttributeValue;
import jakarta.persistence.*;

import java.util.List;

public class ProductAttributeValueCustomRepositoryImpl implements ProductAttributeValueCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tuple> getAttributeOfProduct(String productId) {
        try {
            String sql = "SELECT av.id as id, pa.id as attributeId, pa.name, av.product, av.value FROM product_attribute pa JOIN attribute_value av ON pa.id = av.attribute WHERE av.product = ?1";
            Query query = entityManager.createNativeQuery(sql, Tuple.class);
            query.setParameter(1, productId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ProductAttributeValue findProductAttributeValueOfProduct(Long attributeId, String productId) {
        try {
            String sql = "SELECT a FROM ProductAttributeValue a WHERE a.attribute = :attributeId AND a.product = :productId";
            TypedQuery<ProductAttributeValue> query = entityManager.createQuery(sql, ProductAttributeValue.class);
            query.setParameter("attributeId", attributeId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
