package com.roadmap.ecommerce.dto.user;

import java.time.Instant;
import java.util.UUID;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users", itemRelation = "user")
public record UserResponseDTO(
        UUID id,
        String username,
        boolean admin,
        Instant createdAt,
        Instant updatedAt) {
}