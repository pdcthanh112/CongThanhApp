package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.repository.ShippingRepository;
import com.congthanh.project.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

}
