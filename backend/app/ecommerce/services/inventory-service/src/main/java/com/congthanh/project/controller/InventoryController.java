package com.congthanh.project.controller;

import com.congthanh.project.cqrs.command.command.ReleaseInventoryCommand;
import com.congthanh.project.cqrs.command.command.ReserveInventoryCommand;
import com.congthanh.project.model.request.InventoryRequest;
import com.congthanh.project.model.dto.InventoryDTO;
import com.congthanh.project.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/ecommerce/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    private final CommandGateway commandGateway;

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
    public ResponseEntity<Boolean> isInStock(@PathVariable String sku, @RequestParam int quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(sku, quantity));
    }

    @PostMapping("/reserve")
    public CompletableFuture<String> reserveInventory(@RequestBody ReserveInventoryCommand command) {
        return commandGateway.send(new ReserveInventoryCommand(UUID.randomUUID().toString(), command.getOrderId(), command.getOrderLineItems()));
    }

    @PostMapping("/release")
    public CompletableFuture<String> releaseInventory(@RequestBody ReleaseInventoryCommand command) {
        return commandGateway.send(new ReleaseInventoryCommand(UUID.randomUUID().toString(), command.getOrderId(), command.getOrderLineItems()));
    }

}
