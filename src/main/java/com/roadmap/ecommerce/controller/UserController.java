package com.roadmap.ecommerce.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.roadmap.ecommerce.assembler.UserAssembler;
import com.roadmap.ecommerce.dto.user.UserRequestDTO;
import com.roadmap.ecommerce.dto.user.UserResponseDTO;
import com.roadmap.ecommerce.exception.AlreadyExistsException;
import com.roadmap.ecommerce.exception.EntityNotFoundException;
import com.roadmap.ecommerce.model.User;
import com.roadmap.ecommerce.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO data) {
        if (repository.existsByUsername(data.username())) {
            throw new AlreadyExistsException("User", "username", data.username());
        }

        User user = new User();
        user.setUsername(data.username());
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(data.password()));

        User savedUser = repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedUser));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> findAll() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(assembler.toCollectionModel(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> findById(@PathVariable UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        return ResponseEntity.ok(assembler.toModel(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> update(@PathVariable UUID id,
            @RequestBody @Valid UserRequestDTO data) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        if (!user.getUsername().equals(data.username()) && repository.existsByUsername(data.username())) {
            throw new AlreadyExistsException("User", "username", data.username());
        }

        user.setUsername(data.username());
        user.setPassword(passwordEncoder.encode(data.password()));

        User updatedUser = repository.save(user);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        repository.delete(user);
        return ResponseEntity.noContent().build();
    }
}