package com.roadmap.ecommerce.service;

import com.roadmap.ecommerce.dto.user.UserRequestDTO;
import com.roadmap.ecommerce.exception.AlreadyExistsException;
import com.roadmap.ecommerce.exception.EntityNotFoundException;
import com.roadmap.ecommerce.model.User;
import com.roadmap.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User create(UserRequestDTO data) {
        if (repository.existsByUsername(data.username())) {
            throw new AlreadyExistsException("User", "username", data.username());
        }

        User user = new User();
        user.setUsername(data.username());
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(data.password()));

        return repository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", "ID", id));
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    @Transactional
    public User update(UUID id, UserRequestDTO data) {
        User user = findById(id);

        if (!user.getUsername().equals(data.username()) && repository.existsByUsername(data.username())) {
            throw new AlreadyExistsException("User", "username", data.username());
        }

        user.setUsername(data.username());
        user.setPassword(passwordEncoder.encode(data.password()));

        return repository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        User user = findById(id);
        repository.delete(user);
    }
}