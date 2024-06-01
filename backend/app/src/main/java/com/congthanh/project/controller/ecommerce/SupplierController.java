package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.dto.ecommerce.SupplierDTO;
import com.congthanh.project.entity.ecommerce.Supplier;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.repository.ecommerce.supplier.SupplierRepository;
import com.congthanh.project.service.ecommerce.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository storeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Response<SupplierDTO>> getStoreById(@PathVariable String id) {
        SupplierDTO store = supplierService.getSupplierById(id);
        Response<SupplierDTO> response = new Response<>();
        response.setData(store);
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

    @GetMapping("/getProductFromStore")
    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductFromStore(@RequestParam("store") String storeId, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        ResponseWithPagination<ProductDTO> data = supplierService.getProductFromSupplier(storeId, page, limit);
        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @QueryMapping(value = "supplier")
    List<Supplier> stores() {
        return storeRepository.findAll();
    }
}
