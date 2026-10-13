package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public boolean inStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {
        boolean exist = inventoryRepository.existsBySku(inventoryRequestDTO.getSku());
        if (exist) {
            throw new RuntimeException("Inventory with SKU " + inventoryRequestDTO.getSku() + " already exists");
        }

        Inventory inventory = mapper.toInventory(inventoryRequestDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);

        log.info("Created Inventory with SKU {}", inventory.getSku());

        return mapper.toInventoryResponseDTO(savedInventory);

    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(mapper::toInventoryResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponseDTO getInventoryById(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for productId: " + productId));

        return mapper.toInventoryResponseDTO(inventory);
    }

    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found for id: " + id));
        mapper.updateInventoryFromRequest(inventoryRequestDTO, inventory);

        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Updated Inventory with ID {}", inventory.getId());

        return mapper.toInventoryResponseDTO(updatedInventory);
    }

    @Override
    public void deleteInventoryById(Long productId) {
        if(!inventoryRepository.existsById(productId)) {
            throw new RuntimeException("Inventory not found for productId: " + productId);
        }
        inventoryRepository.deleteById(productId);
        log.info("Deleted Inventory with SKU {}", productId);
    }

    @Override
    @Transactional
    public void reduceStock(String sku, Integer quantity) {

        var inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(
                        () -> new RuntimeException("Inventory not found for SKU: " + sku)
                );
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for SKU: " + sku);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
        log.info("Reduced stock for SKU {} by {}", sku, quantity);

    }
}
