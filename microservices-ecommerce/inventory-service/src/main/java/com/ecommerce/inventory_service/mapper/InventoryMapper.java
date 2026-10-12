package com.ecommerce.inventory_service.mapper;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    Inventory toInventory(InventoryRequestDTO inventoryDTO);

    InventoryResponseDTO toInventoryResponseDTO(Inventory inventory);

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponseDTO toResponse(Inventory inventory);

    @Mapping(target = "id", ignore = true)
    void updateInventoryFromRequest(InventoryRequestDTO inventoryDTO, @MappingTarget Inventory inventory);

}
