package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.document.CartDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDocumentRepository extends MongoRepository<CartDocument, Long>, CartDocumentCustomRepository {
}
