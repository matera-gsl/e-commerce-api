package com.roadmap.ecommerce.dto.auth;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken) {
}