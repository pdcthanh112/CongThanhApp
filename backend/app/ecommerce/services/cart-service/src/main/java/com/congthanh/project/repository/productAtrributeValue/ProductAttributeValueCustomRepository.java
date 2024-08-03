package com.congthanh.project.repository.productAtrributeValue;

import com.congthanh.project.entity.ProductAttributeValue;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductAttributeValueCustomRepository {

    List<Tuple> getAttributeOfProduct(String productId);

    ProductAttributeValue findProductAttributeValueOfProduct(Long attributeId, String productId);

}
