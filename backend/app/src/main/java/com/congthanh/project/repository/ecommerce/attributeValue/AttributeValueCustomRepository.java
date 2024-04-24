package com.congthanh.project.repository.ecommerce.attributeValue;

import com.congthanh.project.entity.ecommerce.AttributeValue;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AttributeValueCustomRepository {

    List<Tuple> getAttributeOfProduct(String productId);

    AttributeValue findAttributeValueOfProduct(Long attributeId, String productId);

}
