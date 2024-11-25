package com.congthanh.productservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubcategoryGrpcService {

    @GrpcClient("catalog-service")
    private final SubcategoryServiceGrpc.SubcategoryServiceBlockingStub blockingStub;

    public SubcategoryGrpcService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.blockingStub = SubcategoryServiceGrpc.newBlockingStub(channel);
    }

    public SubcategoryResponse getSubcategoryById(int subcategoryId) {
        SubcategoryRequest request = SubcategoryRequest.newBuilder()
                .setSubcategoryId(subcategoryId)
                .build();
        return blockingStub.getSubcategoryById(request);
    }
}
