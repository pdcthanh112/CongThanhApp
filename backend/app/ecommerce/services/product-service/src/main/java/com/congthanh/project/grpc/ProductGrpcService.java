package com.congthanh.project.grpc;

import com.congthanh.project.model.dto.ProductDTO;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.service.ProductService;
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

