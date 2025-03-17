package com.congthanh.cartservice.service.serviceImpl;

import com.congthanh.cartservice.cqrs.command.command.AddItemToCartCommand;
import com.congthanh.cartservice.exception.ecommerce.NotFoundException;
import com.congthanh.cartservice.grpc.client.ProductGrpcClient;
import com.congthanh.cartservice.model.document.CartItemDocument;
import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.entity.CartItem;
import com.congthanh.cartservice.model.mapper.CartItemMapper;
import com.congthanh.cartservice.model.request.AddItemToCartRequest;
import com.congthanh.cartservice.model.viewmodel.CartItemDetailVm;
import com.congthanh.cartservice.repository.cart.CartDocumentRepository;
import com.congthanh.cartservice.repository.cartItem.CartItemRepository;
import com.congthanh.cartservice.service.CartItemService;
import com.congthanh.cartservice.utils.SnowflakeIdGenerator;
import com.congthanh.productservice.grpc.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartDocumentRepository cartDocumentRepository;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final ProductGrpcClient productGrpcClient;

    @Override
    public List<CartItemDTO> getItemByCartId(Long cartId) {
        List<CartItem> data = cartItemRepository.getAllCartItemByCartId(cartId);
        List<CartItemDTO> result = new ArrayList<>();
        for (CartItem item : data) {
            CartItemDTO itemDTO = CartItemMapper.mapCartItemEntityToDTO(item);
            result.add(itemDTO);
        }
        return result;
    }

    @Override
    public CartItemDetailVm getCartItemDetail(Long cartId, Long itemId) {
        CartItemDocument data = cartDocumentRepository.getCartItemDetail(cartId, itemId).orElseThrow(() -> new NotFoundException("CartItem not found"));
//        ProductResponse product = productGrpcClient.getProductById(data.getProductId());
        ProductResponse product = ProductResponse.newBuilder()
                .setId("a7c24431-8c3d-4b8b-b425-a226c31e4485")
                .setPrice(123456)
                .setName("Product Name nè")
                .setSlug("iphone-16-pro-max")
                .setSku("88432858468438")
                .build();
        CartItemDetailVm result = CartItemDetailVm.builder()
                .id(data.getId())
                .quantity(data.getQuantity())
                .product(CartItemDetailVm.Product.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .slug(product.getSlug())
                        .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdP-HLEOL8lPE3pRK8RBrCNnKzdDKYtE4UXQ&s")
                        .build())
                .build();
        return result;
    }

    @Override
    @Transactional
    public CartItemDTO addToCart(Long cartId, AddItemToCartRequest request) {
        AddItemToCartCommand command = AddItemToCartCommand.builder()
                .id(snowflakeIdGenerator.nextId())
                .productId(request.getProductId())
                .productVariantId(request.getProductQuantityId())
                .quantity(request.getQuantity())
                .createdAt(Instant.now())
                .cartId(cartId)
                .build();
        commandGateway.send(command);
        return null;


//        CartItem checkExistProduct = cartItemRepository.checkExistProductFromCart(request.getCartId(), request.getProductId());
//        if (checkExistProduct == null) {
//            Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new NotFoundException("cart not found"));
////      ProductRequest request = ProductRequest.newBuilder()
////              .setProductId(productId)
////              .build();
////      ProductResponse product = productServiceStub.getProductById(request);
////        assert product != null;
//            CartItem cartItem = CartItem.builder()
////              .product(product.getId())
//                    .quantity(request.getQuantity())
//                    .cart(cart)
//                    .createdAt(Instant.now())
//                    .build();
//            CartItem result = cartItemRepository.save(cartItem);
//            return CartItemMapper.mapCartItemEntityToDTO(result);
//        } else {
//            checkExistProduct.setQuantity(checkExistProduct.getQuantity() + request.getQuantity());
//            CartItem result = cartItemRepository.save(checkExistProduct);
//            return CartItemMapper.mapCartItemEntityToDTO(result);
//        }
    }

    @Override
    @Transactional
    public CartItemDTO updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.setQuantity(quantity);
        CartItem result = cartItemRepository.save(cartItem);
        CartItemDTO response = CartItemDTO.builder()
                .id(result.getId())
                .quantity(result.getQuantity())
                .product(result.getProductId())
                .productVariant(result.getProductVariant())
                .cart(result.getCartId().getId())
                .build();
        return response;
    }

    @Override
    @Transactional
    public boolean deleteCartItem(Long cartItemId) {
        try {
            cartItemRepository.deleteById(cartItemId);
//            cartItemRepository.deleteCartItemById(cartItemId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
