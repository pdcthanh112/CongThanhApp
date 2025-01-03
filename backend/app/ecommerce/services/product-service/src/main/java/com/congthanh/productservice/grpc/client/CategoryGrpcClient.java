package com.congthanh.productservice.grpc.client;

import com.congthanh.catalogservice.grpc.CategoryRequest;
import com.congthanh.catalogservice.grpc.CategoryResponse;
import com.congthanh.catalogservice.grpc.CategoryServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryGrpcClient {

    @GrpcClient("catalog-service")
    private final CategoryServiceGrpc.CategoryServiceBlockingStub blockingStub;

    public CategoryResponse getCategoryById(String categoryId) {
        CategoryRequest request = CategoryRequest.newBuilder()
                .setCategoryId(categoryId)
                .build();
        return blockingStub.getCategoryById(request);
    }
}
