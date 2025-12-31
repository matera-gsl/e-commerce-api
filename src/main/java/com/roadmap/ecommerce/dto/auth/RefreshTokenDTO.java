package com.roadmap.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(
        @NotBlank String refreshToken) {
}
