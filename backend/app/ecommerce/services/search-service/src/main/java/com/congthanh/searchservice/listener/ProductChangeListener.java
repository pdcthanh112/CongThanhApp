package com.congthanh.searchservice.listener;

import com.congthanh.searchservice.model.document.Product;
import com.congthanh.searchservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductChangeListener {

    private ProductRepository productRepository;

    @KafkaListener(topics = "product-changes")
    public void handleProductChange(String message) {
        try {
            ProductChangeEvent event = parseEvent(message);
            handleEventType(event);
        } catch (Exception e) {
            log.error("Error processing change event", e);
        }
    }

    private void handleEventType(ProductChangeEvent event) {
        switch (event.getOperation()) {
            case CREATE:
                createProduct(event);
                break;
            case UPDATE:
                updateProduct(event);
                break;
            case DELETE:
                deleteProduct(event);
                break;
        }
    }

    private void createProduct(ProductChangeEvent event) {
        Product data = convertToDocument(event.getPayload());
        productRepository.save(data);
    }

    // Tương tự cho update và delete
}
