package com.roadmap.ecommerce.dto.product;

import com.roadmap.ecommerce.validation.ValidPrice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequestDTO(
                @NotBlank String name,
                @NotBlank String description,
                @NotNull @ValidPrice Object price,
                @NotNull @Positive Integer quantity) {
}
