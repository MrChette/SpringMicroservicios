package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    public boolean inStock(@PathVariable String sku,@RequestParam("quantity") Integer quantity) {
        return inventoryService.inStock(sku, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO createProduct(@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO) {
        return inventoryService.createInventory(inventoryRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDTO updateInventory(@PathVariable Long id,@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO) {
        return inventoryService.updateInventory(id,inventoryRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventoryById(@PathVariable Long id) {
        inventoryService.deleteInventoryById(id);
    }

    @PutMapping(value = "/reduce-stock/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean reduceStock(@PathVariable String sku,@RequestParam("quantity") Integer quantity) {
        inventoryService.reduceStock(sku, quantity);
        return true;
    }


    @GetMapping("/test-fail")
    public void testFail() {
        throw new RuntimeException("test fail");
    }



}
