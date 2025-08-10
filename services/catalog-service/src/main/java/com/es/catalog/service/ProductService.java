package com.es.catalog.service;

import com.es.catalog.client.MediaServiceClient;
import com.es.catalog.dto.ProductRequestDto;
import com.es.catalog.dto.ProductResponseDto;
import com.es.catalog.exception.ProductNotFoundException;
import com.es.catalog.mapper.ProductMapper;
import com.es.catalog.model.Product;
import com.es.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final MediaServiceClient mediaClient;

    public ProductService(ProductRepository repository, MediaServiceClient mediaClient) {
        this.repository = repository;
        this.mediaClient = mediaClient;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {
        Product product = ProductMapper.toEntity(request);

        // No file upload here — imageUrl comes from request DTO
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            product.setImageUrl(request.getImageUrl());
        }

        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        Product saved = repository.save(product);
        return ProductMapper.toDto(saved);
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

        // Update image URL if provided
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            existing.setImageUrl(request.getImageUrl());
        }

        existing.setUpdatedAt(Instant.now());

        Product updated = repository.save(existing);
        return ProductMapper.toDto(updated);
    }


    public void deleteProduct(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
