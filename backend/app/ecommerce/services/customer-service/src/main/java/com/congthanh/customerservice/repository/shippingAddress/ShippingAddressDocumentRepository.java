package com.congthanh.customerservice.repository.shippingAddress;

import com.congthanh.customerservice.model.document.ShippingAddressDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressDocumentRepository extends MongoRepository<ShippingAddressDocument, String>, ShippingAddressDocumentCustomRepository {
}
