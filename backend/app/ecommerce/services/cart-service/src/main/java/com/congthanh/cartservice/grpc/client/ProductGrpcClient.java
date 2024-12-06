package com.congthanh.cartservice.grpc.client;

import com.congthanh.productservice.grpc.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductGrpcClient {

    @GrpcClient("product-service")
    private final ProductServiceGrpc.ProductServiceBlockingStub blockingStub;

}
