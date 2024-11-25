package com.congthanh.shippingservice.service.serviceImpl;

import com.congthanh.shippingservice.repository.ShippingRepository;
import com.congthanh.shippingservice.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

}
