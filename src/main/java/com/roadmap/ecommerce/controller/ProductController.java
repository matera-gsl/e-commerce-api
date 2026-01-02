package com.roadmap.ecommerce.controller;

import java.util.UUID;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadmap.ecommerce.assembler.ProductAssembler;
import com.roadmap.ecommerce.dto.product.ProductRequestDTO;
import com.roadmap.ecommerce.dto.product.ProductResponseDTO;
import com.roadmap.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ProductAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<ProductResponseDTO>> create(@RequestBody ProductRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(data)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductResponseDTO>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductResponseDTO>> update(@PathVariable UUID id,
            @RequestBody @Valid ProductRequestDTO data) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, data)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
