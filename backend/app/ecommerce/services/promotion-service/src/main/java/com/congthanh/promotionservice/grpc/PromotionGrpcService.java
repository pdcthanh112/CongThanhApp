package com.congthanh.promotionservice.grpc;

import com.congthanh.promotionservice.service.PromotionService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PromotionGrpcService extends PromotionServiceGrpc.PromotionServiceImplBase {

    private final PromotionService promotionService;

}
