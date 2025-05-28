package com.congthanh.authservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/auth")
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return "test";
    }

}
