package com.roadmap.ecommerce.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.roadmap.ecommerce.assembler.UserAssembler;
import com.roadmap.ecommerce.dto.user.UserRequestDTO;
import com.roadmap.ecommerce.dto.user.UserResponseDTO;
import com.roadmap.ecommerce.model.User;
import com.roadmap.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserAssembler assembler;
    private final UserService service;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(service.create(data)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> findAll(Pageable pageable) {
        Page<User> usersPage = service.findAll(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usersPage, assembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> update(@PathVariable UUID id,
            @RequestBody @Valid UserRequestDTO data) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, data)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}