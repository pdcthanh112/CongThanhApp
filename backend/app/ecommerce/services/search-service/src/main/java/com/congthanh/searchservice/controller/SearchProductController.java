package com.congthanh.searchservice.controller;

import com.congthanh.searchservice.constant.enums.SortType;
import com.congthanh.searchservice.model.request.QueryCriteria;
import com.congthanh.searchservice.model.response.ProductListGetVm;
import com.congthanh.searchservice.model.response.ProductNameListVm;
import com.congthanh.searchservice.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchProductController {

    private final SearchProductService productService;

    @GetMapping("/storefront/catalog-search")
    public ResponseEntity<ProductListGetVm> findProductAdvance(@RequestParam(defaultValue = "") String keyword,
                                                               @RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "12") Integer size,
                                                               @RequestParam(required = false) String brand,
                                                               @RequestParam(required = false) String category,
                                                               @RequestParam(required = false) String attribute,
                                                               @RequestParam(required = false) Double minPrice,
                                                               @RequestParam(required = false) Double maxPrice,
                                                               @RequestParam(defaultValue = "DEFAULT")
                                                               SortType sortType) {
        return ResponseEntity.ok(productService.findProductAdvance(
                new QueryCriteria(keyword, page, size, brand, category, attribute, minPrice, maxPrice, sortType)
        ));
    }

    @GetMapping("/storefront/search_suggest")
    public ResponseEntity<ProductNameListVm> productSearchAutoComplete(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.autoCompleteProductName(keyword));
    }

}
