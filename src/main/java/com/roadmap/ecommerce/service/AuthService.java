package com.roadmap.ecommerce.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roadmap.ecommerce.dto.auth.TokenResponseDTO;
import com.roadmap.ecommerce.exception.InvalidTokenException;
import com.roadmap.ecommerce.model.User;
import com.roadmap.ecommerce.dto.auth.LoginRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenResponseDTO authenticate(LoginRequestDTO data) {
        User user = userService.findByUsername(data.username());

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO refreshToken(String refreshToken) {
        String username = tokenService.validateToken(refreshToken, "refresh");

        if (username.isEmpty()) {
            throw new InvalidTokenException();
        }

        User user = userService.findByUsername(username);

        String newAccessToken = tokenService.generateAccessToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        return new TokenResponseDTO(newAccessToken, newRefreshToken);
    }
}
