package com.congthanh.promotionservice.grpc.service;

import com.congthanh.promotionservice.grpc.GetPromotionByCodeRequest;
import com.congthanh.promotionservice.grpc.PromotionResponse;
import com.congthanh.promotionservice.grpc.PromotionServiceGrpc;
import com.congthanh.promotionservice.model.entity.Promotion;
import com.congthanh.promotionservice.repository.PromotionRepository;
import com.congthanh.promotionservice.service.PromotionService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PromotionGrpcService extends PromotionServiceGrpc.PromotionServiceImplBase {

    private final PromotionRepository promotionRepository;

    private final PromotionService promotionService;

    @Override
    public void getPromotionByCode(GetPromotionByCodeRequest request, StreamObserver<PromotionResponse> responseObserver) {
        try {
            Promotion promotion = promotionRepository.getPromotionByCode(request.getCode());
            PromotionResponse promotionResponse = PromotionResponse.newBuilder()
                    .setId(promotion.getId())
                    .setCode(promotion.getCode())
                    .setValue(promotion.getValue())
                    .setUsageLimit(promotion.getUsageLimit())
                    .setDescription(promotion.getDescription())
                    .setStatus(promotion.getStatus())
                    .build();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

}
