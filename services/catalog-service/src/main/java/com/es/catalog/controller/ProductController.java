package com.es.catalog.controller;

import com.es.catalog.dto.ProductRequestDto;
import com.es.catalog.dto.ProductResponseDto;
import com.es.catalog.model.Product;
import com.es.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto request) {
        return service.createProduct(request);
    }


    @GetMapping
    public List<ProductResponseDto> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable String id) {
        return service.getProductById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseDto update(
            @PathVariable String id,
            @Valid @RequestBody ProductRequestDto request) {
        return service.updateProduct(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteProduct(id);
    }
}
