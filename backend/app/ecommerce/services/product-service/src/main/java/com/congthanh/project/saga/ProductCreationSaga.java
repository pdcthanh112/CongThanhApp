package com.congthanh.project.saga;

import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.kafka.core.KafkaTemplate;

@Saga
public class ProductCreationSaga {

    private transient KafkaTemplate<String, String> kafkaTemplate;

    @StartSaga
    @SagaEventHandler(associationProperty = "productId")
    public void handle(ProductCreatedEvent event) {
        // Notify other services about the new product
        kafkaTemplate.send("product-created", event.getId(), event.getName());
    }

}
