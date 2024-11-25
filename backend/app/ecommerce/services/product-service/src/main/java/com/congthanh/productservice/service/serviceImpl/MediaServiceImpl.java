package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.productservice.service.AbstractCircuitBreakFallbackHandler;
import com.congthanh.productservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl extends AbstractCircuitBreakFallbackHandler implements MediaService {
}
