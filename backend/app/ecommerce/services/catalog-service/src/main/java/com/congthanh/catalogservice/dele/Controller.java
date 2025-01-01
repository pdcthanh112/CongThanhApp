package com.congthanh.catalogservice.dele;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/category")
public class Controller {

    @Autowired
    private CategorySyncService categorySyncService;

    @GetMapping("/sync")
    public ResponseEntity<String> syncCategories() {
        categorySyncService.syncCategories();
        return ResponseEntity.ok("Categories synchronized successfully");
    }
}
