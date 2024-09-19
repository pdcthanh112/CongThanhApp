package com.congthanh.project.grpc;

import com.congthanh.project.service.SubcategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class SubcategoryGrpcService extends SubcategoryServiceGrpc.SubcategoryServiceImplBase {

    private final SubcategoryService subcategoryService;

    @Override
    public void getSubcategoryById(SubcategoryRequest request, StreamObserver<SubcategoryResponse> responseObserver) {


    }

}
