package com.congthanh.searchservice.kafka.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class ProductCdcKafkaListenerConfig extends BaseKafkaListenerConfig<ProductMsgKey, ProductCdcMessage>{

    public static final String PRODUCT_CDC_LISTENER_CONTAINER_FACTORY = "productCdcListenerContainerFactory";

    public ProductCdcKafkaListenerConfig(KafkaProperties kafkaProperties) {
        super(ProductMsgKey.class, ProductCdcMessage.class, kafkaProperties);
    }

    @Bean(name = PRODUCT_CDC_LISTENER_CONTAINER_FACTORY)
    @Override
    public ConcurrentKafkaListenerContainerFactory<ProductMsgKey, ProductCdcMessage> listenerContainerFactory() {
        return super.kafkaListenerContainerFactory();
    }

}
