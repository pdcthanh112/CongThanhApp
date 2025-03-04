package com.congthanh.customerservice.service.serviceImpl;

import com.congthanh.customerservice.exception.ecommerce.NotFoundException;
import com.congthanh.customerservice.model.dto.WishlistDTO;
import com.congthanh.customerservice.model.entity.Wishlist;
import com.congthanh.customerservice.model.entity.WishlistItem;
import com.congthanh.customerservice.repository.wishlist.WishlistRepository;
import com.congthanh.customerservice.repository.wishlist.item.WishlistItemRepository;
import com.congthanh.customerservice.service.WishlistService;
import com.congthanh.customerservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final WishlistItemRepository wishlistItemRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public boolean addProductToWishlist(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.getWishlistByCustomer(customerId);

        if (wishlist == null) {
            wishlist = Wishlist.builder()
                    .customerId(customerId)
                    .build();
            wishlist = wishlistRepository.save(wishlist);
        } else if (wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            throw new RuntimeException("Item already exists!");
        }
        WishlistItem item = WishlistItem.builder()
                .id(snowflakeIdGenerator.nextId())
                .wishlistId(wishlist.getId())
                .productId(productId)
                .createdAt(Instant.now())
                .build();
        wishlistItemRepository.save(item);
        return true;
    }

    @Override
    public boolean removeProductFromWishlist(String customerId, String productId) {
        try {
            wishlistItemRepository.removeItemFromWishlist(customerId, productId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("fjalsf");
        }
    }

    @Override
    public WishlistDTO getWishlistByCustomer(String customerId) {
        Wishlist data = wishlistRepository.getWishlistByCustomer(customerId);
        if (data == null) {
            throw new NotFoundException("Wishlist not found");
        }
        List<WishlistItem> items = wishlistItemRepository.findByWishlistIdOrderByCreatedAtAsc(data.getId());
        System.out.println("wishlist items " + items);
        WishlistDTO result = WishlistDTO.builder()
                .id(data.getId())
                .customer(customerId)
                .product(items.stream().map(WishlistItem::getProductId).collect(Collectors.toSet()))
                .build();
        return result;
    }
}