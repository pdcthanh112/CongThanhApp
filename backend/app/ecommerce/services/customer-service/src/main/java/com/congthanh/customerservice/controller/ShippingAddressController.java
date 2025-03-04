package com.congthanh.customerservice.controller;

import com.congthanh.customerservice.constant.common.ResponseStatus;
import com.congthanh.customerservice.model.dto.ShippingAddressDTO;
import com.congthanh.customerservice.model.request.CreateShippingAddressRequest;
import com.congthanh.customerservice.model.request.UpdateShippingAddressRequest;
import com.congthanh.customerservice.model.response.Response;
import com.congthanh.customerservice.service.ShippingAddressService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/customer")
@Tag(name = "Address API", description = "Address API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @GetMapping("/address/{id}")
    public ResponseEntity<Response<ShippingAddressDTO>> getAddressById(@PathVariable("id") Long addressId) {
        ShippingAddressDTO data = shippingAddressService.getAddressById(addressId);
        Response<ShippingAddressDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = ShippingAddressDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class)))})
    public ResponseEntity<Response<ShippingAddressDTO>> createAddress(@RequestBody CreateShippingAddressRequest request) {
        ShippingAddressDTO data = shippingAddressService.createAddress(request);
        Response<ShippingAddressDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("Create successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Response<ShippingAddressDTO>> updateAddress(@PathVariable("id") Long addressId, @RequestBody UpdateShippingAddressRequest request) {
        ShippingAddressDTO data = shippingAddressService.updateAddress(addressId, request);
        Response<ShippingAddressDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("update successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Response<String>> deleteAddress(@PathVariable("id") Long addressId) {
        shippingAddressService.deleteAddress(addressId);
        Response<String> response = new Response<>();
        response.setMessage("delete successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{customerId}/address")
    public ResponseEntity<Response<List<ShippingAddressDTO>>> getAddressByCustomer(@PathVariable("customerId") String customerId) {
        List<ShippingAddressDTO> data = shippingAddressService.getAddressByCustomer(customerId);
        Response<List<ShippingAddressDTO>> response = new Response<>();
        response.setData(data);
        response.setMessage("get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{customerId}/address/default")
    public ResponseEntity<Response<ShippingAddressDTO>> getDefaultAddressOfCustomer(@PathVariable("customerId") String customerId) {
        ShippingAddressDTO data = shippingAddressService.getDefaultAddressOfCustomer(customerId);
        Response<ShippingAddressDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}