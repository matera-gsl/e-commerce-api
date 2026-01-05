package com.roadmap.ecommerce.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.roadmap.ecommerce.model.Product;

public class ProductSpecs {

    public static Specification<Product> byName(String name) {
        return (root, query, cb) -> (name == null || name.isBlank()) ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}