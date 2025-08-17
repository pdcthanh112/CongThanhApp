package com.congthanh.mediaservice.service.serviceImpl;

import com.congthanh.mediaservice.repository.MediaFileRepository;
import com.congthanh.mediaservice.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaFileServiceImpl implements MediaFileService {

    private final MediaFileRepository mediaFileRepository;
}
