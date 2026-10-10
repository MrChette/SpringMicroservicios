package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    ProductResponseDTO deleteProduct(Long id);

}
