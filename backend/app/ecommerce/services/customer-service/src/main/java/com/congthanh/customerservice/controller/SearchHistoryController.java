package com.congthanh.customerservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/search-history")
@Tag(name = "Address API", description = "Address API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class SearchHistoryController {
}
