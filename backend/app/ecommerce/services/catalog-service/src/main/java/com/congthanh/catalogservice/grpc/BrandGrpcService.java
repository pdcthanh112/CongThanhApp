package com.congthanh.catalogservice.grpc;

import com.congthanh.catalogservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class BrandGrpcService extends BrandServiceGrpc.BrandServiceImplBase{

    private final BrandService brandService;

}