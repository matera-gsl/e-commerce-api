package com.roadmap.ecommerce.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        boolean admin,
        Instant createdAt,
        Instant updatedAt) {
}