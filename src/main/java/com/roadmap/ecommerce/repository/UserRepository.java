package com.roadmap.ecommerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roadmap.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);
}
