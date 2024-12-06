package com.congthanh.productservice.grpc.service;

import com.congthanh.productservice.grpc.ProductRequest;
import com.congthanh.productservice.grpc.ProductResponse;
import com.congthanh.productservice.grpc.ProductServiceGrpc;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.catalogservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    @Override
    public void getProductById(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductDTO product = productService.getProductById(request.getProductId());
        if (product != null) {
            ProductResponse response = ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else responseObserver.onError(new NotFoundException("Product not found"));
    }

}

