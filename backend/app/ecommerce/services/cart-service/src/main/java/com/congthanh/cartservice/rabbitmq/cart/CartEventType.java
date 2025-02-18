package com.congthanh.cartservice.rabbitmq.cart;

public enum CartEventType {
    CREATE_CART,
    UPDATE_CART,
    DELETE_CART,
    ADD_ITEM_TO_CART,
    UPDATE_CART_ITEM,
    REMOVE_ITEM_FROM_CART,
}
