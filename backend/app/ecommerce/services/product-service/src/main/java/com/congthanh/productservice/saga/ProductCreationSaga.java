//package com.congthanh.productservice.saga;
//
//import com.congthanh.productservice.cqrs.command.event.ProductCreatedEvent;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.axonframework.modelling.saga.SagaEventHandler;
//import org.axonframework.modelling.saga.StartSaga;
//import org.axonframework.spring.stereotype.Saga;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.kafka.core.KafkaTemplate;
//
//@Saga
//public class ProductCreationSaga {
//
//    private CommandGateway commandGateway;
//
//    private RabbitTemplate rabbitTemplate;
//
//    private transient KafkaTemplate<String, String> kafkaTemplate;
//
//    @StartSaga
//    @SagaEventHandler(associationProperty = "productId")
//    public void handle(ProductCreatedEvent event) {
////        // Notify other services about the new product
////        kafkaTemplate.send("product-created", event.getId(), event.getName());
////
////        // 1. Synchronize to MongoDB
////        rabbitTemplate.convertAndSend("product.sync", event);
////
////        // 2. Create ProductVariants
////        event.getVariants().forEach(variant ->
////                kafkaTemplate.send("product-variant-creation", new CreateProductVariantCommand(variant))
////        );
////
////        // 3. Create ProductImages
////        event.getImages().forEach(image ->
////                kafkaTemplate.send("product-image-creation", new CreateProductImageCommand(image))
////        );
////
////        // 4. Create ProductAttributes
////        event.getAttributes().forEach(attribute ->
////                kafkaTemplate.send("product-attribute-creation", new CreateProductAttributeCommand(attribute))
////        );
//    }
//
//}
