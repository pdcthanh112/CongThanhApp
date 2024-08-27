package com.congthanh.project.grpc;

import com.congthanh.project.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class BrandGrpcService extends BrandServiceGrpc.BrandServiceImplBase{

    private final BrandRepository brandRepository;

}
