package com.congthanh.project.repository.product;

import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.ProductImage;
import com.congthanh.project.entity.ecommerce.ProductVariant;
import com.congthanh.project.enums.OrderStatus;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Optional<Product> getProductDetailById(String productId) {
        String sql = "SELECT p FROM Product p JOIN FETCH p.image JOIN FETCH p.variant JOIN FETCH p.variant JOIN FETCH p.attribute WHERE p.id = :productId";
        TypedQuery<Product> query = entityManager.createQuery(sql, Product.class);
        query.setParameter("productId", productId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        String sql = "SELECT product.* FROM Product JOIN category on product.category = category.id JOIN subcategory on product.subcategory = subcategory.id WHERE CONCAT(product.name, category.name, subcategory.name, product.slug) ILIKE :keyword";
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

    @Override
    public List<Tuple> getProductAttributeValueByProductId(String productId) {
        String sql = "SELECT product_attribute_value.id, product_attribute_value.value, product_attribute_value.product, product_attribute.name as attribute " +
                "FROM product JOIN product_attribute_value ON product.id = product_attribute_value.product " +
                "JOIN product_attribute ON product_attribute.id = product_attribute_value.attribute " +
                "WHERE product.id = ?1";
        Query query = entityManager.createNativeQuery(sql, Tuple.class);
        query.setParameter(1, productId);
        return query.getResultList();
    }

    @Override
    public List<ProductVariant> getVariantByProductId(String productId) {
        String sql = "SELECT pv FROM ProductVariant pv WHERE pv.product.id = :productId";
        TypedQuery<ProductVariant> query = entityManager.createQuery(sql, ProductVariant.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public List<ProductImage> getImageByProduct(String productId) {
        String sql = "SELECT i FROM ProductImage i WHERE i.product.id = :productId ORDER BY isDefault DESC NULLS LAST";
        TypedQuery<ProductImage> query = entityManager.createQuery(sql, ProductImage.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public List<Tuple> getVariantAttributeValueByProduct(String productId) {
        String sql = "SELECT vav.id as variantAttributeId, va.name as variantAttributeName, vav.value as variantAttributeValue, vav.variantId as variantId FROM VariantAttribute va JOIN VariantAttributeValue vav ON va.id = vav.attributeId\n" +
                "WHERE vav.variantId IN (SELECT pv.id from ProductVariant pv where pv.product.id = :productId)";
        Query query = entityManager.createQuery(sql, Tuple.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

}
