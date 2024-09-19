package com.congthanh.project.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryGrpcService {

    @GrpcClient("catalog-service")
    private final CategoryServiceGrpc.CategoryServiceBlockingStub blockingStub;

    public CategoryGrpcService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.blockingStub = CategoryServiceGrpc.newBlockingStub(channel);
    }

    public CategoryResponse getCategoryById(int categoryId) {
        CategoryRequest request = CategoryRequest.newBuilder()
                .setCategoryId(categoryId)
                .build();
        return blockingStub.getCategoryById(request);
    }
}
