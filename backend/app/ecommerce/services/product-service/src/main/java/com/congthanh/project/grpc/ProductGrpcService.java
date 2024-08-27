package com.congthanh.project.grpc;

import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.repository.product.ProductRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository productRepository;

    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        productRepository.findById(request.getProductId())
                .ifPresentOrElse(
                        product -> {
                            ProductResponse response = ProductResponse.newBuilder()
                                    .setId(product.getId())
                                    .setName(product.getName())
                                    .build();

                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(new NotFoundException("Product not found"))
                );
    }
}

