package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ProductViewDTO;
import com.congthanh.project.model.request.AddProductViewRequest;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.ProductViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<ProductViewDTO>> addProductView(@RequestBody AddProductViewRequest requestData, HttpSession session) {
        ProductViewDTO result = productViewService.addProductView(requestData.getProductId(), requestData.getCustomerId());
        Response<ProductViewDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
