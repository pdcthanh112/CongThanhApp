package com.congthanh.cartservice.service.serviceImpl;

import com.congthanh.cartservice.constant.common.StateStatus;
import com.congthanh.cartservice.constant.enums.CartStatus;
import com.congthanh.cartservice.cqrs.command.command.CreateCartCommand;
import com.congthanh.cartservice.model.document.CartDocument;
import com.congthanh.cartservice.model.dto.CartDTO;
import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.model.entity.CartItem;
import com.congthanh.cartservice.exception.ecommerce.NotFoundException;
import com.congthanh.cartservice.model.mapper.CartMapper;
import com.congthanh.cartservice.model.request.CreateCartRequest;
import com.congthanh.cartservice.model.viewmodel.CartVm;
import com.congthanh.cartservice.repository.cart.CartDocumentRepository;
import com.congthanh.cartservice.repository.cartItem.CartItemRepository;
import com.congthanh.cartservice.repository.cart.CartRepository;
import com.congthanh.cartservice.service.CartService;
import com.congthanh.cartservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartDocumentRepository cartDocumentRepository;

    private final CartItemRepository cartItemRepository;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart NOT FOUND"));

        CartDTO result = new CartDTO();
        result.setId(cart.getId());
        result.setName(cart.getName());
        result.setCustomer(cart.getCustomerId());
        result.setCreatedAt(cart.getCreatedAt());
        result.setStatus(cart.getStatus());

        List<CartItem> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
        if (listCartItem.size() > 0) {
            Set<CartItemDTO> cartItems = new HashSet<>();
            for (CartItem cartItemItem : listCartItem) {
                CartItemDTO cartItemTmp = new CartItemDTO();
                cartItemTmp.setId(cartItemItem.getId());
                cartItemTmp.setQuantity(cartItemItem.getQuantity());
                cartItemTmp.setCart(cart.getId());
                cartItemTmp.setCreatedAt(cartItemItem.getCreatedAt());
//                cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));
                cartItemTmp.setProduct(cartItemItem.getProductId());

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
                cartTmp.setCustomer(cart.getCustomerId());
                cartTmp.setStatus(cart.getStatus());
                cartTmp.setCreatedAt(cart.getCreatedAt());
                List<CartItem> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
                if (listCartItem.size() > 0) {
                    Set<CartItemDTO> cartItems = new HashSet<>();
                    for (CartItem cartItemItem : listCartItem) {
                        CartItemDTO cartItemTmp = new CartItemDTO();
                        cartItemTmp.setId(cartItemItem.getId());
                        cartItemTmp.setQuantity(cartItemItem.getQuantity());
                        cartItemTmp.setCart(cart.getId());
//                        cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));
                        cartItemTmp.setProduct(cartItemItem.getProductId());

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
    public List<CartVm> customerGetCartByCustomerId(String customerId) {
        List<CartDocument> result = cartDocumentRepository.getCartDocumentsByCustomerId(customerId);
        return result.stream().map(CartMapper::mapCartDocumentToVm).collect(Collectors.toList());
    }

    @Override
    public CartDTO createCart(CreateCartRequest request) {
        CreateCartCommand cart = CreateCartCommand.builder()
                .id(snowflakeIdGenerator.nextId())
                .name(request.getName())
                .customerId(request.getCustomerId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .isDefault(request.isDefault())
                .status(CartStatus.valueOf(StateStatus.STATUS_ACTIVE))
                .build();
        var result = commandGateway.sendAndWait(cart);
        return null;
    }

    @Override
    public CartDTO updateCart(CartDTO cartDTO) {
        return null;
    }

    @Override
    public boolean deleteCart(Long cartId) {
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
    public boolean setDefaultCartForCustomer(String customerId, Long cartId) {
        return cartRepository.setDefaultCartForCustomer(customerId, cartId);
    }

}





