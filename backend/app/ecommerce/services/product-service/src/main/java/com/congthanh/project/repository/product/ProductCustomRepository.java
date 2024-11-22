package com.congthanh.project.repository.product;

import com.congthanh.project.model.entity.Product;
import com.congthanh.project.model.entity.ProductImage;
import com.congthanh.project.model.entity.ProductVariant;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductCustomRepository {

    @EntityGraph(value = "Product.WithAll", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> getProductDetailById(String productId);

    List<Product> searchProduct(String keyword);

    boolean checkExistSlug(String slug);

    Long countTotalSoldProduct(String productId);

    List<Tuple> getProductAttributeValueByProductId(String productId);

    List<ProductVariant> getVariantByProductId(String productId);

    List<ProductImage> getImageByProduct(String productId);

    Object getVariantAttributeValueByProduct(String productId);
}
