package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.repository.brand.BrandRepository;
import com.congthanh.project.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

}
