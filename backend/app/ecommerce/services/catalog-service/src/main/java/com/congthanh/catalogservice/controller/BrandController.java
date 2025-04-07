package com.congthanh.catalogservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.catalogservice.model.dto.BrandDTO;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.catalogservice.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/brands")
@Tag(name = "Category API", description = "Brand API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping()
    public ResponseEntity<Response<List<BrandDTO>>> getAllBrand() {
        List<BrandDTO> result = brandService.getAllBrand();
        Response<List<BrandDTO>> response = new Response<>();
        response.setData(result);
        response.setMessage("Get all successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}
