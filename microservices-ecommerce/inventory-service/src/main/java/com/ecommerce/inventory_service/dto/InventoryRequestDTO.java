package com.ecommerce.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDTO {

    @NotBlank(message = "SKU must not be blank")
    private String sku;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;
}
