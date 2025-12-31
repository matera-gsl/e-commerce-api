package com.roadmap.ecommerce.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.roadmap.ecommerce.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final int ACCESS_TOKEN_EXPIRATION_HOURS = 2;
    private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 7;

    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_EXPIRATION_HOURS, "access");
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_EXPIRATION_DAYS * 24, "refresh");
    }

    private String generateToken(User user, int hours, String type) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ecommerce-api")
                    .withSubject(user.getUsername())
                    .withClaim("type", type)
                    .withClaim("role", user.isAdmin() ? "ADMIN" : "USER")
                    .withExpiresAt(genExpirationDate(hours))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating " + type + " token", exception);
        }
    }

    public String validateToken(String token, String expectedType) {
        System.out.println(token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decodedJWT = JWT.require(algorithm)
                    .withIssuer("ecommerce-api")
                    .build()
                    .verify(token);

            String tokenType = decodedJWT.getClaim("type").asString();

            if (!expectedType.equals(tokenType)) {
                return "";
            }

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw exception;
            // return "";
        }
    }

    private Instant genExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
    }
}