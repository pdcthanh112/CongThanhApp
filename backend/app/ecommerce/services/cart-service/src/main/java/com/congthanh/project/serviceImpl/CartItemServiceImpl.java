package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.CartItemDTO;
import com.congthanh.project.entity.Cart;
import com.congthanh.project.entity.CartItem;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.CartItemMapper;
import com.congthanh.project.model.mapper.CartMapper;
import com.congthanh.project.model.response.ProductResponse;
import com.congthanh.project.repository.cartItem.CartItemRepository;
import com.congthanh.project.repository.cart.CartRepository;
import com.congthanh.project.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;

  private final CartRepository cartRepository;

  private final WebClient webClient;

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
      ProductResponse product = webClient.get().uri("/product/" + productId).retrieve().bodyToMono(ProductResponse.class).block();
        assert product != null;
        CartItem cartItem = CartItem.builder()
              .product(product.getId())
              .quantity(quantity)
              .cart(cart)
              .createdAt(new Date().getTime())
              .build();
      CartItem result =  cartItemRepository.save(cartItem);
      CartItemDTO response = CartItemMapper.mapCartItemEntityToDTO(result);
      return response;
    } else {
      checkExistProduct.setQuantity(checkExistProduct.getQuantity() + quantity);
      CartItem result = cartItemRepository.save(checkExistProduct);
      CartItemDTO response = CartItemMapper.mapCartItemEntityToDTO(result);
      return response;
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
            .product(ProductResponse.builder().build())
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
