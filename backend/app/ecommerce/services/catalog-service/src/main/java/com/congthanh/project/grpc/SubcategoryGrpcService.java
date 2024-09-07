package com.congthanh.project.grpc;

import com.congthanh.project.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class SubcategoryGrpcService extends SubcategoryServiceGrpc.SubcategoryServiceImplBase {

    private final SubcategoryService subcategoryService;

}
