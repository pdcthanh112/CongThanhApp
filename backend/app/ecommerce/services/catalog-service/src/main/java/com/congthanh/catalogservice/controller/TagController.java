package com.congthanh.catalogservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.catalogservice.model.dto.TagDTO;
import com.congthanh.catalogservice.model.request.CreateTagRequest;
import com.congthanh.catalogservice.model.request.UpdateTagRequest;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.catalogservice.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/tag")
@Tag(name = "Product Tag API", description = "Product tag API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/getAll")
    public ResponseEntity<Response<ResponseWithPagination<TagDTO>>> getAllTags(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int limit,
                                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                                               @RequestParam(defaultValue = "ASC") String sortDirection) {
        ResponseWithPagination<TagDTO> data = tagService.getAllTags();
        Response<ResponseWithPagination<TagDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getById")
    public ResponseEntity<Response<TagDTO>> getTagById(@RequestParam("id") Long id) {
        TagDTO data = tagService.getTagById(id);
        Response<TagDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<TagDTO>> createTag(@RequestBody CreateTagRequest request) {
        TagDTO data = tagService.createTag(request);
        Response<TagDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<TagDTO>> updateTag(@RequestBody UpdateTagRequest request, @PathVariable("id") Long tagId) {
        TagDTO data = tagService.updateTag(request, tagId);
        Response<TagDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Updated successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add-product-tag")
    public ResponseEntity<Response<?>> addTagFromProduct(@RequestBody Map<String, String> requestData) {
        Long tagId = Long.valueOf(requestData.get("tagId"));
        String productId = requestData.get("productId");
        boolean result = tagService.addTagFromProduct(tagId, productId);
        Response<String> response = new Response<>();
        if (result) {
            response.setData("true");
            response.setStatus(ResponseStatus.STATUS_SUCCESS);
            response.setMessage("Add successfully");
        } else {
            response.setData(null);
            response.setStatus(ResponseStatus.STATUS_FAILED);
            response.setMessage("already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/remove-product-tag")
    public ResponseEntity<Response<?>> removeTagFromProduct(@RequestBody Map<String, String> requestData) {
        Long tagId = Long.valueOf(requestData.get("tagId"));
        String productId = requestData.get("productId");
        boolean result = tagService.removeTagFromProduct(tagId, productId);
        Response<String> response = new Response<>();
        if (result) {
            response.setData("true");
            response.setStatus(ResponseStatus.STATUS_SUCCESS);
            response.setMessage("Remove successfully");
        } else {
            response.setData(null);
            response.setStatus(ResponseStatus.STATUS_FAILED);
            response.setMessage("already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
