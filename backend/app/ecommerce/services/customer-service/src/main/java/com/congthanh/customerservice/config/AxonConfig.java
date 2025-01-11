package com.congthanh.customerservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

//    @Bean
//    public EventStorageEngine storageEngine(EntityManagerProvider entityManagerProvider,
//                                            TransactionManager transactionManager) {
//        return JpaEventStorageEngine.builder()
//                .entityManagerProvider(entityManagerProvider)
//                .transactionManager(transactionManager)
//                .build();
//    }
//
//    @Bean
//    public CommandBus commandBus(TransactionManager transactionManager) {
//        return SimpleCommandBus.builder()
//                .transactionManager(transactionManager)
//                .build();
//    }
//
//    @Bean
//    public TokenStore tokenStore(EntityManagerProvider entityManagerProvider, Serializer serializer) {
//        return JpaTokenStore.builder()
//                .entityManagerProvider(entityManagerProvider)
//                .serializer(serializer)
//                .build();
//    }

}
