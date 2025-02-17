package com.congthanh.cartservice.repository.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartDocumentCustomRepositoryImpl implements CartDocumentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void setDefaultCartForCustomer(String customerId, Long cartId) {

    }
}
