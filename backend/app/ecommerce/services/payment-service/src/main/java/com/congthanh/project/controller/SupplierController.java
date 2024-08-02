package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.dto.SupplierDTO;
import com.congthanh.project.entity.ecommerce.Supplier;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.repository.supplier.SupplierRepository;
import com.congthanh.project.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/supplier")
@Tag(name = "Supplier API", description = "Supplier API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    private final SupplierRepository supplierRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Response<SupplierDTO>> getSupplierById(@PathVariable String id) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        Response<SupplierDTO> response = new Response<>();
        response.setData(supplier);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("get successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-by-slug")
    public ResponseEntity<Response<SupplierDTO>> getSupplierBySlug(@RequestParam("slug") String slug) {
        SupplierDTO supplier = supplierService.getSupplierBySlug(slug);
        Response<SupplierDTO> response = new Response<>();
        response.setData(supplier);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("get successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<Supplier>> createStore(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.createSupplier(supplierDTO);
        Response<Supplier> response = new Response<>();
        response.setData(supplier);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getProductFromSupplier")
    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductFromStore(@RequestParam("supplier") String storeId, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        ResponseWithPagination<ProductDTO> data = supplierService.getProductFromSupplier(storeId, page, limit);
        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @QueryMapping(value = "supplier")
    List<Supplier> suppliers() {
        return supplierRepository.findAll();
    }
}
