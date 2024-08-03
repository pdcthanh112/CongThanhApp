package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.repository.shipping.ShippingRepository;
import com.congthanh.project.service.ecommerce.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

}
