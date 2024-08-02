package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.VoucherDTO;
import com.congthanh.project.entity.ecommerce.Voucher;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
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
