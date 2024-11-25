package com.congthanh.catalogservice.grpc;

import com.congthanh.catalogservice.service.TagService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class TagGrpcService extends TagServiceGrpc.TagServiceImplBase {

    private final TagService tagService;

}
