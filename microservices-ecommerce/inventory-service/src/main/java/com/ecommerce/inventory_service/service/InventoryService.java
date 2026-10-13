package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    boolean inStock(String sku, Integer quantity);
    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO getInventoryById(Long productId);
    InventoryResponseDTO updateInventory(Long id,InventoryRequestDTO inventoryRequestDTO);
    void deleteInventoryById(Long productId);
    void reduceStock(String sku, Integer quantity);

}
