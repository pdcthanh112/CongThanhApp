package com.congthanh.project.service;

import com.congthanh.project.dto.VoucherDTO;

public interface VoucherService {

    VoucherDTO getVoucherByCode(String code);

    VoucherDTO createVoucher(VoucherDTO voucherDTO);

    VoucherDTO updateVoucher(String voucherId, VoucherDTO voucherDTO);

    boolean checkValidVoucher(String code);
}
