package com.congthanh.cartservice.service.serviceImpl;

import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.dto.ProductDTO;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.model.entity.CartItem;
import com.congthanh.cartservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.grpc.ProductRequest;
import com.congthanh.productservice.grpc.ProductResponse;
import com.congthanh.productservice.grpc.ProductServiceGrpc;
import com.congthanh.cartservice.model.mapper.CartItemMapper;
import com.congthanh.cartservice.model.mapper.CartMapper;
import com.congthanh.cartservice.repository.cartItem.CartItemRepository;
import com.congthanh.cartservice.repository.cart.CartRepository;
import com.congthanh.cartservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;

  private final CartRepository cartRepository;

  @GrpcClient("product-service")
  private final ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  @Override
  public List<CartItemDTO> getItemByCartId(String cartId) {
    List<CartItem> data = cartItemRepository.getAllCartItemByCartId(cartId);
    List<CartItemDTO> result = new ArrayList<>();
    for (CartItem item: data) {
      CartItemDTO itemDTO = CartItemMapper.mapCartItemEntityToDTO(item);
      result.add(itemDTO);
    }
    return result;
  }

  @Override
  public CartItemDTO addToCart(String productId, int quantity, String cartId) {
    CartItem checkExistProduct = cartItemRepository.checkExistProductFromCart(cartId, productId);
    if (checkExistProduct == null) {
      Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("cart not found"));
      ProductRequest request = ProductRequest.newBuilder()
              .setProductId(productId)
              .build();
      ProductResponse product = productServiceStub.getProductById(request);
        assert product != null;
        CartItem cartItem = CartItem.builder()
              .product(product.getId())
              .quantity(quantity)
              .cart(cart)
              .createdAt(new Date().getTime())
              .build();
      CartItem result =  cartItemRepository.save(cartItem);
      return CartItemMapper.mapCartItemEntityToDTO(result);
    } else {
      checkExistProduct.setQuantity(checkExistProduct.getQuantity() + quantity);
      CartItem result = cartItemRepository.save(checkExistProduct);
      return CartItemMapper.mapCartItemEntityToDTO(result);
    }
  }

  @Override
  public CartItemDTO updateCartItem(String cartItemId, int quantity) {
    CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
    cartItem.setQuantity(quantity);
    CartItem result = cartItemRepository.save(cartItem);
    CartItemDTO response = CartItemDTO.builder()
            .id(result.getId())
            .quantity(result.getQuantity())
//            .product(ProductMapper.mapProductEntityToDTO(result.getProduct()))
            .product(ProductDTO.builder().id(result.getProduct()).build())
            .cart(CartMapper.mapCartEntityToDTO(result.getCart()))
            .build();
    return response;
  }

  @Override
  public boolean deleteCartItem(String cartItemId) {
    try {
      cartItemRepository.deleteById(cartItemId);
//            cartItemRepository.deleteCartItemById(cartItemId);
      return true;
    } catch (Exception e) {
      return false;
    }

  }
}
