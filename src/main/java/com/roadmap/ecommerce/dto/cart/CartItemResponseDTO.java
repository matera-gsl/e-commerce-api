package com.roadmap.ecommerce.dto.cart;

import java.util.UUID;

public record CartItemResponseDTO(
        UUID productId,
        String productName,
        Integer quantity,
        String unitPrice,
        String subtotal) {
}