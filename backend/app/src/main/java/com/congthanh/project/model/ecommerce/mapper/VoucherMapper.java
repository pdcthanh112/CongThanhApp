package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.VoucherDTO;
import com.congthanh.project.entity.ecommerce.Voucher;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Voucher mapVoucherDTOToEntity(VoucherDTO voucherDTO) {
        return modelMapper.map(voucherDTO, Voucher.class);
    }

    public VoucherDTO mapVoucherEntityToDTO(Voucher voucher) {
        return modelMapper.map(voucher, VoucherDTO.class);
    }

}
