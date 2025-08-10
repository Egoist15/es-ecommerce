package com.es.catalog.controller;

import com.es.catalog.dto.ProductRequestDto;
import com.es.catalog.dto.ProductResponseDto;
import com.es.catalog.model.Product;
import com.es.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto request) {
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

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable String id, @Valid @RequestBody ProductRequestDto request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteProduct(id);
    }
}
