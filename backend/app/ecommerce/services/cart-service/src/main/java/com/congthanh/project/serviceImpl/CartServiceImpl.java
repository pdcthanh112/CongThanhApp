package com.congthanh.project.serviceImpl;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.CartDTO;
import com.congthanh.project.dto.CartItemDTO;
import com.congthanh.project.entity.Cart;
import com.congthanh.project.entity.CartItem;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.CartMapper;
import com.congthanh.project.model.mapper.ProductMapper;
import com.congthanh.project.repository.cartItem.CartItemRepository;
import com.congthanh.project.repository.cart.CartRepository;
import com.congthanh.project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public CartDTO getCartById(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart NOT FOUND"));

        CartDTO result = new CartDTO();
        result.setId(cart.getId());
        result.setName(cart.getName());
        result.setCustomer(cart.getCustomer());
        result.setCreatedDate(cart.getCreatedAt());
        result.setStatus(cart.getStatus());

        List<CartItem> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
        if (listCartItem.size() > 0) {
            Set<CartItemDTO> cartItems = new HashSet<>();
            for (CartItem cartItemItem : listCartItem) {
                CartItemDTO cartItemTmp = new CartItemDTO();
                cartItemTmp.setId(cartItemItem.getId());
                cartItemTmp.setQuantity(cartItemItem.getQuantity());
                cartItemTmp.setCart(CartMapper.mapCartEntityToDTO(cart));
                cartItemTmp.setCreatedDate(cartItemItem.getCreatedAt());
                cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));

                cartItems.add(cartItemTmp);
            }
            result.setCartItems(cartItems);
        }
        return result;
    }

    @Override
    public List<CartDTO> getActiveCartByCustomerId(String customerId) {
        List<CartDTO> response = new ArrayList<>();
        List<Cart> listCart = cartRepository.findActiveCartByCustomerId(customerId);
        if (!listCart.isEmpty()) {
            for (Cart cart : listCart) {
                CartDTO cartTmp = new CartDTO();
                cartTmp.setId(cart.getId());
                cartTmp.setName(cart.getName());
                cartTmp.setCustomer(cart.getCustomer());
                cartTmp.setStatus(cart.getStatus());
                cartTmp.setCreatedDate(cart.getCreatedAt());
                List<CartItem> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
                if (listCartItem.size() > 0) {
                    Set<CartItemDTO> cartItems = new HashSet<>();
                    for (CartItem cartItemItem : listCartItem) {
                        CartItemDTO cartItemTmp = new CartItemDTO();
                        cartItemTmp.setId(cartItemItem.getId());
                        cartItemTmp.setQuantity(cartItemItem.getQuantity());
                        cartItemTmp.setCart(CartMapper.mapCartEntityToDTO(cart));
                        cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));

                        cartItems.add(cartItemTmp);
                    }
                    cartTmp.setCartItems(cartItems);
                }
                response.add(cartTmp);
            }
        } else {
            return null;
        }
        return response;
    }

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = Cart.builder()
                .name(cartDTO.getName())
                .customer(cartDTO.getCustomer())
                .isDefault(cartDTO.isDefault())
                .status(StateStatus.STATUS_ACTIVE)
                .build();

        Cart result = cartRepository.save(cart);
        if (cartDTO.isDefault()) {
            this.setDefaultCartForCustomer(cartDTO.getCustomer(), result.getId());
        }
        return CartMapper.mapCartEntityToDTO(result);
    }

    @Override
    public CartDTO updateCart(CartDTO cartDTO) {
        return null;
    }

    @Override
    public boolean deleteCart(String cartId) {
        try {
            cartRepository.deleteById(cartId);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("id khong ton tai");
        }
    }

    @Override
    public CartDTO getDefaultCartOfCustomer(String customerId) {
        Cart data = cartRepository.getDefaultCartOfCustomer(customerId);
        if (data != null) {
            return CartMapper.mapCartEntityToDTO(data);
        }
        return null;
    }

    @Override
    public boolean setDefaultCartForCustomer(String customerId, String cartId) {
        return cartRepository.setDefaultCartForCustomer(customerId, cartId);
    }

}





