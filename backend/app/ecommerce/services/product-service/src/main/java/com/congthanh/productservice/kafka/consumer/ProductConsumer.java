package com.congthanh.productservice.kafka.consumer;

import com.congthanh.productservice.model.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "product-topic", groupId = "product-group")
    public void consumeProduct(String message) throws JsonProcessingException {
        ProductDTO product = objectMapper.readValue(message, ProductDTO.class);
        // Process the received product
    }

    @KafkaListener(topics = "all-products-topic", groupId = "product-group")
    public void consumeAllProducts(String message) throws JsonProcessingException {
        List<ProductDTO> products = objectMapper.readValue(message, new TypeReference<List<ProductDTO>>() {
        });
        // Process the received products
    }

}
