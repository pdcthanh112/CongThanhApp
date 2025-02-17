package com.congthanh.cartservice.constant.common;

public class RabbitMQConstants {

    public static class Cart {
        public static final String QUEUE_NAME = "cart_queue";
        public static final String EXCHANGE = "cart_exchange";
        public static final String ROUTING_KEY = "cart.routing.key";
        public static final String QUEUE_CREATE = "cart.queue.create";
        public static final String QUEUE_UPDATE = "cart.queue.update";
        public static final String QUEUE_DELETE = "cart.queue.delete";
    }

    public static class CartItem {
        public static final String QUEUE_NAME = "brand_queue";
        public static final String EXCHANGE = "brand_exchange";
        public static final String ROUTING_KEY = "brand.routing.key";
        public static final String QUEUE_CREATE = "brand.queue.create";
        public static final String QUEUE_UPDATE = "brand.queue.update";
        public static final String QUEUE_DELETE = "brand.queue.delete";
        public static final String ROUTING_CREATE = "brand.event.create";
    }

}
