package com.roadmap.ecommerce.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.roadmap.ecommerce.dto.product.ProductRequestDTO;
import com.roadmap.ecommerce.exception.EntityNotFoundException;
import com.roadmap.ecommerce.mapper.ProductMapper;
import com.roadmap.ecommerce.model.Product;
import com.roadmap.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product create(ProductRequestDTO data) {
        Product product = productMapper.toEntity(data);
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", "ID", id));
    }

    @Transactional
    public Product update(UUID id, ProductRequestDTO data) {
        findById(id);
        Product updatedProduct = productMapper.toEntity(data);
        return productRepository.save(updatedProduct);
    }

    @Transactional
    public void delete(UUID id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
