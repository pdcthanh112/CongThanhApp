package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ProductViewDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.ProductViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/product-view")
@Tag(name = "Product View API", description = "Product View API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductViewService productViewService;

    @GetMapping("/get-total-view-product")
    public ResponseEntity<Response<Long>> getTotalViewOfProduct(@RequestParam("product") String productId) {
        Long result = productViewService.getTotalViewOfProduct(productId);
        Response<Long> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add-product-view")
    public ResponseEntity<Response<ProductViewDTO>> addProductView(@RequestBody Map<String, String> requestData) {
        String productId = requestData.get("productId");
        String customerId = requestData.get("customerId");
        ProductViewDTO result = productViewService.addProductView(productId, customerId);
        Response<ProductViewDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
