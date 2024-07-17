package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.VoucherDTO;
import com.congthanh.project.entity.ecommerce.Voucher;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Voucher mapVoucherDTOToEntity(VoucherDTO voucherDTO) {
        return modelMapper.map(voucherDTO, Voucher.class);
    }

    public static VoucherDTO mapVoucherEntityToDTO(Voucher voucher) {
        return modelMapper.map(voucher, VoucherDTO.class);
    }

}
