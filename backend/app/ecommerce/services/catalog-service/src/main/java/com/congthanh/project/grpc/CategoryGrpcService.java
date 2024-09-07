package com.congthanh.project.grpc;

import com.congthanh.project.service.CategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CategoryGrpcService extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryService categoryService;

    @Override
    public void getCategoryById(CategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {


    }

}
