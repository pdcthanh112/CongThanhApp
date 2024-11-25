package com.congthanh.cartservice.grpc;

import com.congthanh.cartservice.service.CartItemService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CartItemGrpcService extends CartItemServiceGrpc.CartItemServiceImplBase {

    private final CartItemService cartItemService;

    @Override
    public void getCartItem (CartItemRequest request, StreamObserver<CartItemResponse> responseObserver) {
//        CartItemResponse result = cartItemRepository.getProductById(request.getId())
//                .ifPresentOrElse(
//                        product -> {
//                            Product grpcProduct = Product.newBuilder()
//                                    .setId(product.getId())
//                                    .setName(product.getName())
//                                    .setPrice(product.getPrice())
//                                    .build();
//                            ProductResponse response = ProductResponse.newBuilder()
//                                    .setProduct(grpcProduct)
//                                    .build();
//                            responseObserver.onNext(response);
//                            responseObserver.onCompleted();
//                        },
//                        () -> {
//                            responseObserver.onError(new RuntimeException("Product not found"));
//                        }
//                );
//        return result;
    }

}
