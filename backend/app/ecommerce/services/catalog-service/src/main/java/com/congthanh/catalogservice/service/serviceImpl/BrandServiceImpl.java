package com.congthanh.catalogservice.service.serviceImpl;

import com.congthanh.catalogservice.repository.brand.BrandRepository;
import com.congthanh.catalogservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

}
