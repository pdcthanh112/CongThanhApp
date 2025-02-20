package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.model.document.CartDocument;
import com.congthanh.cartservice.model.document.CartItemDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartDocumentCustomRepositoryImpl implements CartDocumentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void setDefaultCartForCustomer(String customerId, Long cartId) {

    }

    @Override
    public void addItemToCart(Long cartId, CartItemDocument document) {
        // Tìm cart item trong cart có cùng productId và productVariantId
        Query query = new Query(Criteria.where("id").is(cartId)
                .and("cartItems").elemMatch(Criteria.where("productId").is(document.getProductId())
                        .and("productVariantId").is(document.getProductVariantId())));

        CartDocument existingCart = mongoTemplate.findOne(query, CartDocument.class);

        if (existingCart != null && existingCart.getCartItems().stream()
                .anyMatch(item -> item.getProductId().equals(document.getProductId())
                        && item.getProductVariantId().equals(document.getProductVariantId()))) {
            // Nếu item đã tồn tại, cập nhật quantity
            Update update = new Update().inc("cartItems.$.quantity", document.getQuantity());
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("id").is(cartId)
                            .and("cartItems.productId").is(document.getProductId())
                            .and("cartItems.productVariantId").is(document.getProductVariantId())),
                    update,
                    CartDocument.class
            );
        } else {
            // Nếu item chưa tồn tại, thêm mới vào set
            CartItemDocument newItem = CartItemDocument.builder()
                    .id(document.getId()) // Bạn cần implement method này
                    .productId(document.getProductId())
                    .productVariantId(document.getProductVariantId())
                    .quantity(document.getQuantity())
                    .createdAt(document.getCreatedAt())
                    .build();

            Update update = new Update()
                    .push("cartItems", newItem)
                    .set("updatedAt", Instant.now());

            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("id").is(cartId)),
                    update,
                    CartDocument.class
            );
        }
    }

    @Override
    public Optional<CartItemDocument> getCartItemDetail(Long cartId, Long itemId) {
        Query query = new Query(Criteria.where("id").is(cartId));

        // Sử dụng projection để chỉ lấy cart item cần thiết
        query.fields().include("cartItems");

        CartDocument cart = mongoTemplate.findOne(query, CartDocument.class);

        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return Optional.empty();
        }

        // Tìm cart item theo ID trong danh sách cart items
        return cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }
}

