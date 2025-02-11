package com.congthanh.catalogservice.grpc;

import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.service.CategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CategoryGrpcService extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryService categoryService;

    @Override
    public void getCategoryById(CategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try {
            CategoryDTO category = categoryService.getCategoryById(request.getCategoryId());
            CategoryResponse response = CategoryResponse.newBuilder()
                    .setId(category.getId())
                    .setName(category.getName())
                    .setSlug(category.getSlug())
                    .setDescription(category.getDescription())
                    .setImage(category.getImage())
                    .setStatus(category.getStatus().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCategoryBySlug(CategorySlugRequest request, StreamObserver<CategoryResponse> responseObserver) {
        try {
            CategoryDTO category = categoryService.getCategoryById(request.getSlug());
            CategoryResponse response = CategoryResponse.newBuilder()
                    .setId(category.getId())
                    .setName(category.getName())
                    .setSlug(category.getSlug())
                    .setDescription(category.getDescription())
                    .setImage(category.getImage())
                    .setStatus(category.getStatus().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

}
