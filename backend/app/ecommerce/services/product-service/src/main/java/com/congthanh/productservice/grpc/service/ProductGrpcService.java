package com.congthanh.productservice.grpc.service;

import com.congthanh.productservice.grpc.ProductRequest;
import com.congthanh.productservice.grpc.ProductResponse;
import com.congthanh.productservice.grpc.ProductServiceGrpc;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

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
        } else {
            responseObserver.onError(new NotFoundException("Product not found"));
        }
    }

    public void getProductsForOrder(GetProductsForOrderRequest request, StreamObserver<GetProductsForOrderResponse> responseObserver) {
        GetProductsForOrderResponse.Builder responseBuilder = GetProductsForOrderResponse.newBuilder();

        try {
            List<UUID> productUuids = request.getProductIdsList().stream()
                    .map(UUID::fromString)
                    .toList();

            List<Product> products = productRepository.findByIds(productUuids);

            if (products.size() != productUuids.size()) {
                responseBuilder.setErrorCode("NOT_FOUND")
                        .setErrorMessage("Some products not found");
            } else {
                List<ProductDetail> details = new ArrayList<>();
                String shippingProvince = request.getShippingProvinceCode();

                for (Product p : products) {
                    // Basic checks in service (more can be added)
                    if (!p.isActive() || p.isDeleted()) {
                        responseBuilder.setErrorCode("NOT_AVAILABLE")
                                .setErrorMessage("Product " + p.getId() + " is not available");
                        break;
                    }
                    if (!p.isSellerActive()) {
                        responseBuilder.setErrorCode("SELLER_SUSPENDED")
                                .setErrorMessage("Seller suspended for product " + p.getId());
                        break;
                    }
                    if (p.getRestrictedProvinces() != null && p.getRestrictedProvinces().contains(shippingProvince)) {
                        responseBuilder.setErrorCode("SHIPPING_RESTRICTED")
                                .setErrorMessage("Shipping restricted for product " + p.getId() + " to " + shippingProvince);
                        break;
                    }
                    // Add more checks if needed, but client (OrderService) will handle quantity etc.

                    ProductDetail detail = ProductDetail.newBuilder()
                            .setProductId(p.getId().toString())
                            .setSku(p.getSku())
                            .setName(p.getName())
                            .setOriginalPrice(p.getOriginalPrice())
                            .setSellingPrice(p.getSellingPrice())
                            .setIsActive(p.isActive())
                            .setIsDeleted(p.isDeleted())
                            .setSellerId(p.getSellerId())
                            .setSellerActive(p.isSellerActive())
                            .setMaxQuantityPerOrder(p.getMaxQuantityPerOrder())
                            .setIsPreorder(p.isPreorder())
                            .setPreorderEndDate(p.getPreorderEndDate() != null ? p.getPreorderEndDate().toString() : "")
                            .addAllRestrictedProvinces(p.getRestrictedProvinces() != null ? p.getRestrictedProvinces() : List.of())
                            .setVariantId(p.getVariantId() != null ? p.getVariantId() : "")
                            .putAllAttributes(p.getAttributes() != null ? p.getAttributes() : Map.of())
                            .build();

                    details.add(detail);
                }

                if (responseBuilder.getErrorCode().isEmpty()) {
                    responseBuilder.addAllProducts(details);
                }
            }
        } catch (Exception e) {
            responseBuilder.setErrorCode("INTERNAL_ERROR")
                    .setErrorMessage(e.getMessage());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}

