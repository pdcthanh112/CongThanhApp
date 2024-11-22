package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.service.AbstractCircuitBreakFallbackHandler;
import com.congthanh.project.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl extends AbstractCircuitBreakFallbackHandler implements MediaService {
}
