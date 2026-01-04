package com.congthanh.orderservice.grpc.client;

import com.congthanh.inventoryservice.grpc.InventoryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InventoryGrpcClient {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    public InventoryGrpcClient(@Value("${grpc.client.product-service.address}") String serviceAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serviceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        blockingStub = InventoryServiceGrpc.newBlockingStub(channel);
    }
}
