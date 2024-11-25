package com.congthanh.catalogservice.constant.common;

public class RabbitMQConstants {

    public static class Category {
        public static final String QUEUE_NAME = "category_queue";
        public static final String EXCHANGE = "category_exchange";
        public static final String ROUTING_KEY = "category.routing.key";
        public static final String QUEUE_CREATE = "category.queue.create";
        public static final String QUEUE_UPDATE = "category.queue.update";
        public static final String QUEUE_DELETE = "category.queue.delete";
    }

    public static class Brand {
        public static final String QUEUE_NAME = "brand_queue";
        public static final String EXCHANGE = "brand_exchange";
        public static final String ROUTING_KEY = "brand.routing.key";
        public static final String QUEUE_CREATE = "brand.queue.create";
        public static final String QUEUE_UPDATE = "brand.queue.update";
        public static final String QUEUE_DELETE = "brand.queue.delete";
        public static final String ROUTING_CREATE = "brand.event.create";
    }

    public static class Tag {
        public static final String QUEUE_NAME = "tag_queue";
        public static final String EXCHANGE = "tag_exchange";
        public static final String ROUTING_KEY = "tag.routing.key";
        public static final String QUEUE_CREATE = "tag.queue.create";
        public static final String QUEUE_UPDATE = "tag.queue.update";
        public static final String QUEUE_DELETE = "tag.queue.delete";
        public static final String ROUTING_CREATE = "tag.event.create";

    }

}
