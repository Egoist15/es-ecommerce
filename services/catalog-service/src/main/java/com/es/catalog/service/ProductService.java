package com.es.catalog.service;

import com.es.catalog.dto.ProductRequestDto;
import com.es.catalog.dto.ProductResponseDto;
import com.es.catalog.exception.ProductNotFoundException;
import com.es.catalog.mapper.ProductMapper;
import com.es.catalog.model.Product;
import com.es.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {
        Product product = ProductMapper.toEntity(request);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return ProductMapper.toDto(repository.save(product));
    }


    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(String id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDto(product);
    }

    public ProductResponseDto updateProduct(String id, ProductRequestDto request) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setCategory(request.getCategory());
        existing.setImageUrl(request.getImageUrl());
        existing.setUpdatedAt(Instant.now());

        return ProductMapper.toDto(repository.save(existing));
    }

    public void deleteProduct(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
