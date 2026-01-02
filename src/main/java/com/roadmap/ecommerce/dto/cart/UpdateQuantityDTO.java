package com.roadmap.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateQuantityDTO(
        @NotNull @Positive Integer quantity) {
}
