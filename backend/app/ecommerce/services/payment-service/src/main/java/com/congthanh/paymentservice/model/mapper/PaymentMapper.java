package com.congthanh.paymentservice.model.mapper;

import com.congthanh.paymentservice.model.dto.PaymentDTO;
import com.congthanh.paymentservice.model.entity.Payment;
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
