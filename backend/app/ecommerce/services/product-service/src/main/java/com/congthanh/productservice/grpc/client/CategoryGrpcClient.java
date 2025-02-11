package com.congthanh.productservice.grpc.client;

import com.congthanh.catalogservice.grpc.CategoryRequest;
import com.congthanh.catalogservice.grpc.CategoryResponse;
import com.congthanh.catalogservice.grpc.CategoryServiceGrpc;
import com.congthanh.catalogservice.grpc.CategorySlugRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CategoryGrpcClient {

    @GrpcClient("catalog-service")
    private final CategoryServiceGrpc.CategoryServiceBlockingStub blockingStub;

    public CategoryGrpcClient(@Value("${grpc.client.catalog-service.address}") String serviceAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serviceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        blockingStub = CategoryServiceGrpc.newBlockingStub(channel);
    }

    public CategoryResponse getCategoryById(String categoryId) {
        CategoryRequest request = CategoryRequest.newBuilder()
                .setCategoryId(categoryId)
                .build();
        return blockingStub.getCategoryById(request);
    }

    public CategoryResponse getCategoryBySlug(String categoryId) {
        CategorySlugRequest request = CategorySlugRequest.newBuilder()
                .setSlug(categoryId)
                .build();
        return blockingStub.getCategoryBySlug(request);
    }
}
