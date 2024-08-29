package com.congthanh.project.grpc;

import com.congthanh.project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PromotionGrpcService extends PromotionServiceGrpc.PromotionServiceImplBase {

    private final PromotionService promotionService;

}
