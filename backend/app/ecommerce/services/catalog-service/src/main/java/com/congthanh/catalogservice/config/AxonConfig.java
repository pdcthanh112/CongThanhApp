//package com.congthanh.catalogservice.config;
//
//import org.axonframework.commandhandling.CommandBus;
//import org.axonframework.commandhandling.SimpleCommandBus;
//import org.axonframework.common.jpa.EntityManagerProvider;
//import org.axonframework.common.transaction.TransactionManager;
//import org.axonframework.eventhandling.tokenstore.TokenStore;
//import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
//import org.axonframework.serialization.Serializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AxonConfig {
//
////    @Bean
////    public EventStorageEngine storageEngine(EntityManagerProvider entityManagerProvider,
////                                            TransactionManager transactionManager) {
////        return JpaEventStorageEngine.builder()
////                .entityManagerProvider(entityManagerProvider)
////                .transactionManager(transactionManager)
////                .build();
////    }
////
////    @Bean
////    public CommandBus commandBus(TransactionManager transactionManager) {
////        return SimpleCommandBus.builder()
////                .transactionManager(transactionManager)
////                .build();
////    }
////
////    @Bean
////    public TokenStore tokenStore(EntityManagerProvider entityManagerProvider, Serializer serializer) {
////        return JpaTokenStore.builder()
////                .entityManagerProvider(entityManagerProvider)
////                .serializer(serializer)
////                .build();
////    }
//
//}
