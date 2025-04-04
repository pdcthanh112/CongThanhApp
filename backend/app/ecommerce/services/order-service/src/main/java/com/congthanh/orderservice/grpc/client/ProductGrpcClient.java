package com.congthanh.orderservice.grpc.client;

import com.congthanh.productservice.grpc.ProductRequest;
import com.congthanh.productservice.grpc.ProductResponse;
import com.congthanh.productservice.grpc.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductGrpcClient {

    @GrpcClient("product-service")
    private final ProductServiceGrpc.ProductServiceBlockingStub blockingStub;

    public ProductGrpcClient(@Value("${grpc.client.product-service.address}") String serviceAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serviceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        blockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public ProductResponse getProductById(String productId) {
        ProductRequest request = ProductRequest.newBuilder().setProductId(productId).build();
        return blockingStub.getProductById(request);
    }
}
