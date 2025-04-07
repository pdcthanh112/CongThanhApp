package com.congthanh.catalogservice.service.serviceImpl;

import com.congthanh.catalogservice.model.dto.BrandDTO;
import com.congthanh.catalogservice.model.entity.Brand;
import com.congthanh.catalogservice.repository.brand.BrandRepository;
import com.congthanh.catalogservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandDTO> getAllBrand() {
        List<Brand> result = brandRepository.findAll();
        return result.stream().map(item ->
                BrandDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .slug(item.getSlug())
                        .image(item.getImage())
                        .build()
        ).collect(Collectors.toList());
    }
}
