package com.roadmap.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadmap.ecommerce.dto.auth.LoginRequestDTO;
import com.roadmap.ecommerce.dto.auth.RefreshTokenDTO;
import com.roadmap.ecommerce.dto.auth.TokenResponseDTO;
import com.roadmap.ecommerce.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        TokenResponseDTO tokens = authService.authenticate(data);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestBody @Valid RefreshTokenDTO data) {
        String cleanToken = data.refreshToken().replace("\"", "");
        TokenResponseDTO tokens = authService.refreshToken(cleanToken);
        return ResponseEntity.ok(tokens);
    }

}
