package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.TagDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Repository
@RequestMapping("/ecommerce/tag")
@Tag(name = "Product Tag API", description = "Product tag API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/getAll")
    public ResponseEntity<Response<List<TagDTO>>> getAllTags() {
        List<TagDTO> data = tagService.getAllTags();
        Response<List<TagDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<TagDTO>> createTag(@RequestBody TagDTO tagDTO) {
        TagDTO data = tagService.createTag(tagDTO);
        Response<TagDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/add-product-tag")
    public ResponseEntity<Response<?>> addTagFromProduct(@RequestBody Map<String, String> requestData) {
        Long tagId = Long.valueOf(requestData.get("tagId"));
        String productId = requestData.get("productId");
        boolean result = tagService.addTagFromProduct(tagId, productId);
        Response<String> response = new Response<>();
        if(result) {
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
        if(result) {
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
