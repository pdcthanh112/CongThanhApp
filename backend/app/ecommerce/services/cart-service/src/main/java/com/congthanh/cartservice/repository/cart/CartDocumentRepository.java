package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.document.CartDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDocumentRepository extends MongoRepository<CartDocument, Long>, CartDocumentCustomRepository {

    List<CartDocument> getCartDocumentsByCustomerId(String customerId);
}
