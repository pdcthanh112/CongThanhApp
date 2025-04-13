package com.congthanh.orderservice.grpc.client;

import com.congthanh.promotionservice.grpc.GetPromotionByCodeRequest;
import com.congthanh.promotionservice.grpc.PromotionResponse;
import com.congthanh.promotionservice.grpc.PromotionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PromotionGrpcClient {

    @GrpcClient("promotion-service")
    private final PromotionServiceGrpc.PromotionServiceBlockingStub blockingStub;

    public PromotionGrpcClient(@Value("${grpc.client.catalog-service.address}") String serviceAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serviceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        blockingStub = PromotionServiceGrpc.newBlockingStub(channel);
    }

    public PromotionResponse getPromotionByCode(String code) {
        GetPromotionByCodeRequest request = GetPromotionByCodeRequest.newBuilder().setCode(code).build();
        return blockingStub.getPromotionByCode(request);
    }
}
