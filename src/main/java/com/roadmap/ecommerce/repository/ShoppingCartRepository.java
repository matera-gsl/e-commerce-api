package com.roadmap.ecommerce.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roadmap.ecommerce.model.ShoppingCart;
import com.roadmap.ecommerce.model.User;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {

    Optional<ShoppingCart> findByUser(User user);
}
