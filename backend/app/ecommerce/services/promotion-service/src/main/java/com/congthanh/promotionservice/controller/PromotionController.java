package com.congthanh.promotionservice.controller;

import com.congthanh.promotionservice.constant.common.ResponseStatus;
import com.congthanh.promotionservice.model.dto.PromotionDTO;
import com.congthanh.promotionservice.model.response.ErrorDTO;
import com.congthanh.promotionservice.model.response.Response;
import com.congthanh.promotionservice.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/promotion")
@RequiredArgsConstructor
@Slf4j
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/getByCode")
    public ResponseEntity<Object> getPromotionByCode(@RequestParam("code") String code) {
        PromotionDTO data = promotionService.getPromotionByCode(code);
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
    public ResponseEntity<Response<PromotionDTO>> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        PromotionDTO data = promotionService.createPromotion(promotionDTO);
        Response<PromotionDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<PromotionDTO>> updatePromotion(@PathVariable("id") Long promotionId, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO data = promotionService.updatePromotion(promotionId, promotionDTO);
        Response<PromotionDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/checkValid")
    public ResponseEntity<String> validatePromotion(@RequestParam("code") String code) {
        boolean isPromotionValid = promotionService.checkValidPromotion(code);

        if (isPromotionValid) {
            return ResponseEntity.ok("Promotion is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Promotion is not valid");
        }
    }
}
