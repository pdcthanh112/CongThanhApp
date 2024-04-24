package com.congthanh.project.repository.ecommerce.product;

import com.congthanh.project.entity.ecommerce.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductCustomRepository {

    List<Product> searchProduct(String keyword);

    boolean checkExistSlug(String slug);

    Long countTotalSoldProduct(String productId);

}
