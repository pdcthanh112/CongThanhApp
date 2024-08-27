package com.congthanh.project.grpc;

import com.congthanh.project.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class TagGrpcService extends TagServiceGrpc.TagServiceImplBase {

    private final TagRepository tagRepository;

}
