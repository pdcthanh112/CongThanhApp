package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.VoucherDTO;
import com.congthanh.project.entity.Voucher;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.VoucherMapper;
import com.congthanh.project.repository.voucher.VoucherRepository;
import com.congthanh.project.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public VoucherDTO getVoucherByCode(String code) {
        Voucher result = voucherRepository.getVoucherByCode(code);
        return result != null ? VoucherMapper.mapVoucherEntityToDTO(result) : null;
    }

    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        Voucher existVoucher = voucherRepository.getVoucherByCode(voucherDTO.getCode());
        if (existVoucher != null) throw new RuntimeException("Code exits");
        Voucher voucher = Voucher.builder()
                .code(voucherDTO.getCode())
                .type(voucherDTO.getType())
                .value(voucherDTO.getValue())
                .usageLimit(voucherDTO.getUsageLimit())
                .description(voucherDTO.getDescription())
                .startDate(voucherDTO.getStartDate())
                .endDate(voucherDTO.getEndDate())
                .status("NEW")
                .build();
        Voucher result = voucherRepository.save(voucher);
        return VoucherMapper.mapVoucherEntityToDTO(result);
    }

    @Override
    public VoucherDTO updateVoucher(String voucherId, VoucherDTO voucherDTO) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new NotFoundException("voucher not found"));

        voucher.setCode(voucherDTO.getCode());
        voucher.setType(voucherDTO.getType());
        voucher.setValue(voucherDTO.getValue());
        voucher.setUsageLimit(voucherDTO.getUsageLimit());
        voucher.setDescription(voucherDTO.getDescription());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setEndDate(voucherDTO.getEndDate());

        Voucher result = voucherRepository.save(voucher);
        return VoucherMapper.mapVoucherEntityToDTO(result);
    }

    @Override
    public boolean checkValidVoucher(String code) {
        Voucher voucher = voucherRepository.getVoucherByCode(code);

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(voucher.getStartDate()) || currentDate.isAfter(voucher.getEndDate()) || voucher.getStatus().equals("INACTIVE"))
            return false;
        if (voucher.getUsageLimit() == 0) return false;
        return true;
    }
}
