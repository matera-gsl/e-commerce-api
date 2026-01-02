package com.roadmap.ecommerce.dto.cart;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.EntityModel;

public record CartResponseDTO(
                UUID id,
                List<EntityModel<CartItemResponseDTO>> items,
                String total,
                Instant updatedAt) {
}