package com.congthanh.productservice.kafka.producer;

import com.congthanh.productservice.model.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendProduct(ProductDTO product) throws JsonProcessingException {
        String productJson = objectMapper.writeValueAsString(product);
        kafkaTemplate.send("product-topic", productJson);
    }

    public void sendAllProducts(Iterable<ProductDTO> products) throws JsonProcessingException {
        String productsJson = objectMapper.writeValueAsString(products);
        kafkaTemplate.send("all-products-topic", productsJson);
    }
}
