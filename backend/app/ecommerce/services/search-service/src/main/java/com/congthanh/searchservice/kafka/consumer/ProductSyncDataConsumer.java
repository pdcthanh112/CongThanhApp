package com.congthanh.searchservice.kafka.consumer;

import com.congthanh.searchservice.kafka.cdc.BaseCdcConsumer;
import com.congthanh.searchservice.kafka.cdc.RetrySupportDql;
import com.congthanh.searchservice.kafka.cdc.message.ProductCdcMessage;
import com.congthanh.searchservice.kafka.cdc.message.ProductMsgKey;
import com.congthanh.searchservice.service.ProductSyncDataService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.congthanh.searchservice.kafka.cdc.message.Operation.DELETE;
import static com.congthanh.searchservice.kafka.config.ProductCdcKafkaListenerConfig.PRODUCT_CDC_LISTENER_CONTAINER_FACTORY;

@Service
@Slf4j
public class ProductSyncDataConsumer extends BaseCdcConsumer<ProductMsgKey, ProductCdcMessage> {

    private final ProductSyncDataService productSyncDataService;

    public ProductSyncDataConsumer(ProductSyncDataService productSyncDataService) {
        this.productSyncDataService = productSyncDataService;
    }

    @KafkaListener(
            id = "product-sync-es",
            groupId = "product-sync-search",
            topics = "${product.topic.name}",
            containerFactory = PRODUCT_CDC_LISTENER_CONTAINER_FACTORY
    )
    @RetrySupportDql(listenerContainerFactory = PRODUCT_CDC_LISTENER_CONTAINER_FACTORY)
    public void processMessage(
            @Header(KafkaHeaders.RECEIVED_KEY) ProductMsgKey key,
            @Payload(required = false) @Valid ProductCdcMessage productCdcMessage,
            @Headers MessageHeaders headers
    ) {
        processMessage(key, productCdcMessage, headers, this::sync);
    }

    public void sync(ProductMsgKey key, ProductCdcMessage productCdcMessage) {
        boolean isHardDeleteEvent = productCdcMessage == null || DELETE.equals(productCdcMessage.getOp());
        if (isHardDeleteEvent) {
            log.warn("Having hard delete event for product: '{}'", key.getId());
            productSyncDataService.deleteProduct(key.getId());
        } else {
            var operation = productCdcMessage.getOp();
            var productId = key.getId();
            switch (operation) {
                case CREATE, READ -> productSyncDataService.createProduct(productId);
                case UPDATE -> productSyncDataService.updateProduct(productId);
                default -> log.warn("Unsupported operation '{}' for product: '{}'", operation, productId);
            }
        }
    }

}
