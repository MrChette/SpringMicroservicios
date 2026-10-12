package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        Product product = mapper.toProduct(productRequestDTO);

        Product savedProduct = repository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());

        return mapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = repository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));;

        return mapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO) {

        Product product = repository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        mapper.updateProductFromRequest(productRequestDTO, product);

        Product updatedProduct = repository.save(product);
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());

        return mapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        repository.deleteById(id);
        log.info("Product deleted successfully with ID: {}", id);
    }
}
