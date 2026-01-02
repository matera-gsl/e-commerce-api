package com.roadmap.ecommerce.dto.cart;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddToCartRequestDTO(
                @NotNull(message = "Product ID is required") UUID productId,
                @NotNull @Positive Integer quantity) {
}