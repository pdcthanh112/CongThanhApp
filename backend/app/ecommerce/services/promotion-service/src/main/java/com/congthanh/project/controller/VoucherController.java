package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.PromotionDTO;
import com.congthanh.project.model.response.ErrorDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final PromotionService promotionService;

    @GetMapping("/getByCode")
    public ResponseEntity<Object> getVoucherByCode(@RequestParam("code") String code) {
        PromotionDTO data = promotionService.getVoucherByCode(code);
        if (data == null) {
            ErrorDTO error = new ErrorDTO();
            error.setErrorCode(40404);
            error.setMessage("not found code");
            error.setStatus(ResponseStatus.STATUS_FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Response<PromotionDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<PromotionDTO>> createVoucher(@RequestBody PromotionDTO promotionDTO) {
        PromotionDTO data = promotionService.createVoucher(promotionDTO);
        Response<PromotionDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<PromotionDTO>> updateVoucher(@PathVariable("id") String voucherId, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO data = promotionService.updateVoucher(voucherId, promotionDTO);
        Response<PromotionDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/checkValid")
    public ResponseEntity<String> validateVoucher(@RequestParam("code") String code) {
        boolean isVoucherValid = promotionService.checkValidVoucher(code);

        if (isVoucherValid) {
            return ResponseEntity.ok("Voucher is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Voucher is not valid");
        }
    }
}
