package com.congthanh.project.controller;

import com.congthanh.project.model.request.InventoryRequest;
import com.congthanh.project.dto.InventoryDTO;
import com.congthanh.project.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> addInventoryItem(@RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.addInventoryItem(request));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<InventoryDTO> updateInventoryQuantity(@PathVariable String sku, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.updateInventoryQuantity(sku, quantity));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryDTO> getInventoryItem(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getInventoryItem(sku));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> removeInventoryItem(@PathVariable String sku) {
        inventoryService.removeInventoryItem(sku);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sku}/in-stock")
    public ResponseEntity<Boolean> isInStock(@PathVariable String sku, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(sku, quantity));
    }

}
