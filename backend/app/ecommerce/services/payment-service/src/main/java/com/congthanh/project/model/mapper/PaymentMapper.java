package com.congthanh.project.model.mapper;

import com.congthanh.project.model.dto.PaymentDTO;
import com.congthanh.project.model.entity.Payment;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Payment mapPaymentDTOToEntity(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }

    public PaymentDTO mapPaymentEntityToDTO(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }
}
