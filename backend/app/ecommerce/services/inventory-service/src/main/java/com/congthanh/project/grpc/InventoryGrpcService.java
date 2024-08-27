package com.congthanh.project.grpc;

import com.congthanh.project.repository.InventoryRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryRepository inventoryRepository;

    @Override
    public void getInventoryByProduct(InventoryRequest request, StreamObserver<InventoryResponse> responseObserver) {

    }
}
