package com.congthanh.customerservice.constant.common;

public class RabbitMQConstants {

    public static class ShippingAddress {
        public static final String QUEUE_NAME = "shippingAddress_queue";
        public static final String EXCHANGE = "category_exchange";
        public static final String ROUTING_KEY = "shippingAddress.routing.key";
        public static final String QUEUE_CREATE = "category.queue.create";
        public static final String QUEUE_UPDATE = "category.queue.update";
        public static final String QUEUE_DELETE = "category.queue.delete";
    }

    public static class Wishlist {
        public static final String QUEUE_NAME = "brand_queue";
        public static final String EXCHANGE = "brand_exchange";
        public static final String ROUTING_KEY = "brand.routing.key";
        public static final String QUEUE_CREATE = "brand.queue.create";
        public static final String QUEUE_UPDATE = "brand.queue.update";
        public static final String QUEUE_DELETE = "brand.queue.delete";
        public static final String ROUTING_CREATE = "brand.event.create";
    }

}
