package com.roadmap.ecommerce.controller;

import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadmap.ecommerce.assembler.CartAssembler;
import com.roadmap.ecommerce.dto.cart.AddToCartRequestDTO;
import com.roadmap.ecommerce.dto.cart.CartResponseDTO;
import com.roadmap.ecommerce.dto.cart.UpdateQuantityDTO;
import com.roadmap.ecommerce.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartAssembler cartAssembler;

    @GetMapping
    public ResponseEntity<EntityModel<CartResponseDTO>> findUserCart() {
        var cart = cartService.findUserCart();
        return ResponseEntity.ok(cartAssembler.toModel(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<EntityModel<CartResponseDTO>> addItem(@RequestBody @Valid AddToCartRequestDTO dto) {
        var cart = cartService.addItem(dto.productId(), dto.quantity());
        return ResponseEntity.ok(cartAssembler.toModel(cart));
    }

    @PatchMapping("/items/{productId}")
    public ResponseEntity<EntityModel<CartResponseDTO>> updateQuantity(
            @PathVariable UUID productId,
            @RequestBody @Valid UpdateQuantityDTO dto) {

        var cart = cartService.updateItemQuantity(productId, dto.quantity());
        return ResponseEntity.ok(cartAssembler.toModel(cart));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID productId) {
        cartService.removeItem(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}