package com.roadmap.ecommerce.dto.product;

import java.time.Instant;
import java.util.UUID;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "products", itemRelation = "product")
public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        String price,
        Integer quantity,
        Instant createdAt,
        Instant updatedAt) {
}
