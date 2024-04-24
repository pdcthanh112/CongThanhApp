package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.dto.ecommerce.StoreDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.ecommerce.Store;
import com.congthanh.project.repository.ecommerce.store.StoreRepository;
import com.congthanh.project.service.ecommerce.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Response<StoreDTO>> getStoreById(@PathVariable String id) {
        StoreDTO store = storeService.getStoreById(id);
        Response<StoreDTO> response = new Response<>();
        response.setData(store);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("get successfully");
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/create")
    public ResponseEntity<Response<Store>> createStore(@RequestBody StoreDTO storeDTO) {
        Store store = storeService.createStore(storeDTO);
        Response<Store> response = new Response<>();
        response.setData(store);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getProductFromStore")
    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductFromStore(@RequestParam("store") String storeId, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        ResponseWithPagination<ProductDTO> data = storeService.getProductFromStore(storeId, page, limit);
        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @QueryMapping(value = "store")
    List<Store> stores() {
        return storeRepository.findAll();
    }
}
