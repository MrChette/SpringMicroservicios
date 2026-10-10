package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSerciveImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        Product product = mapper.toProduct(productRequestDTO);

        repository.save(product);

        return mapper.toProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(mapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        return repository.findById(String.valueOf(id))
                .map(mapper::toProductResponseDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {

        Product product = repository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return repository.;
    }

    @Override
    public ProductResponseDTO deleteProduct(Long id) {
        return null;
    }
}
