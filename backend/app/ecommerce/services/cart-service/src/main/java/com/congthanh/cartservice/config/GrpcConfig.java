package com.congthanh.cartservice.config;
//
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.springframework.context.annotation.Bean;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//
@Configuration
public class GrpcConfig {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }
//
//    @Bean
//    public ManagedChannel productServiceChannel() {
//        return ManagedChannelBuilder.forAddress("product-service", 5008)
//                .usePlaintext()
//                .build();
//    }
//
//    @Bean
//    public ManagedChannel orderServiceChannel() {
//        return ManagedChannelBuilder.forAddress("order-service", 8081)
//                .usePlaintext()
//                .build();
//    }
//
//    @Bean
//    public ManagedChannel inventoryServiceChannel() {
//        return ManagedChannelBuilder.forAddress("inventory-service", 8082)
//                .usePlaintext()
//                .build();
//    }
//
}
